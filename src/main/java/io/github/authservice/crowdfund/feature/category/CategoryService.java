package io.github.authservice.crowdfund.feature.category;

import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryRepository;
import io.github.authservice.crowdfund.domain.category.mapper.CategoryMapper;
import io.github.authservice.crowdfund.feature.category.request.CategoryNameRequest;
import io.github.authservice.crowdfund.feature.category.request.CreateCategoryRequest;
import io.github.authservice.crowdfund.feature.category.request.PatchCategoryParentRequest;
import io.github.authservice.crowdfund.feature.category.request.PatchCategorySortOrderRequest;
import io.github.authservice.crowdfund.feature.category.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 트리 조회 도메인 로직
     *
     */
    public GetCategoryTreeResponse getCategoryTree() {
        List<Category> allCategories = categoryRepository.findByIsActiveTrue();

        Map<Integer, CategoryNode> nodeMap = allCategories.stream()
                .collect(Collectors.toMap(
                        Category::id,
                        c -> new CategoryNode(c.id(), c.name(), c.depth(), c.sortOrder(), new ArrayList<>())
                ));

        List<CategoryNode> rootNodes = new ArrayList<>();

        for (Category category : allCategories) {
            CategoryNode node = nodeMap.get(category.id());
            if (category.parentId() == null) {
                rootNodes.add(node);
            } else {
                CategoryNode parentNode = nodeMap.get(category.parentId());
                if (parentNode != null) {
                    parentNode.children().add(node);
                }
            }
        }

        return new GetCategoryTreeResponse("카테고리 조회가 완료되었습니다.", rootNodes);
    }

    /**
     * 카테고리 생성 도메인 로직
     *
     */
    @Transactional
    public CreateCategoryResponse createCategory(@Valid CreateCategoryRequest request) {

        // 카테고리의 깊이 계산
        int depth = 1;
        if (request.getParentId() != null) {
            Category parent = categoryRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다. ID: " + request.getParentId()));
            depth = parent.depth() + 1;
        }

        // 카테고리의 정렬 순서 계산 (마지막 순서 + 10)
        int sortOrder = 10;
        List<Category> siblings = categoryRepository.findByParentIdAndIsActiveTrueOrderBySortOrderAsc(request.getParentId());
        if (!siblings.isEmpty()) {
            sortOrder = siblings.stream()
                    .mapToInt(Category::sortOrder)
                    .max()
                    .orElse(0) + 10;
        }

        categoryMapper.insert(
                request,
                depth,
                sortOrder
        );

        Category savedCategory = categoryRepository.findById(request.getId())
                .orElseThrow(() -> new IllegalStateException("카테고리 생성 후 조회를 실패했습니다."));

        return new CreateCategoryResponse("카테고리가 생성되었습니다.", savedCategory);
    }

    /**
     * 카테고리 이름 수정 도메인 로직
     *
     */
    @Transactional
    public PatchCategoryNameResponse patchCategoryName(Integer id, CategoryNameRequest request) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id));

        categoryMapper.updateName(id.longValue(), request.name());

        return new PatchCategoryNameResponse("카테고리 이름이 수정되었습니다.");
    }

    /**
     * 카테고리 부모 변경 도메인 로직
     *
     */
    @Transactional
    public PatchCategoryParentResponse patchCategoryParent(Integer categoryId, PatchCategoryParentRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + categoryId));

        // 자기 자신을 부모로 설정하는지 체크
        if (request.parentId() != null && request.parentId().equals(categoryId)) {
            throw new IllegalArgumentException("자기 자신을 부모로 설정할 수 없습니다.");
        }

        int newDepth = 1;
        if (request.parentId() != null) {
            Category parent = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다. ID: " + request.parentId()));
            newDepth = parent.depth() + 1;
        }

        // 실제 부모가 변경된 경우에만 처리
        if (category.parentId() == null ? request.parentId() != null : !category.parentId().equals(request.parentId())) {
            // 순환 참조 체크 및 하위 노드 깊이 업데이트를 위해 전체 트리를 활용하거나 
            // 여기서는 단순하게 전체 데이터를 다시 가져와서 정합성을 맞춤
            List<Category> allCategories = categoryRepository.findByIsActiveTrue();

            // 이동할 부모가 이동할 카테고리의 자식인지 확인
            if (request.parentId() != null) {
                if (isDescendant(allCategories, categoryId, request.parentId())) {
                    throw new IllegalArgumentException("자식 카테고리를 부모로 설정할 수 없습니다. (순환 참조)");
                }
            }

            // 깊이 차이 계산
            int depthDiff = newDepth - category.depth();

            // 상위 카테고리 업데이트
            categoryMapper.updateParentId(categoryId, request.parentId(), newDepth);

            // 하위 카테고리들의 깊이 일괄 업데이트
            if (depthDiff != 0) {
                updateDescendantsDepth(allCategories, categoryId, depthDiff);
            }
        }

        return new PatchCategoryParentResponse("카테고리 부모가 변경되었습니다.");
    }

    private boolean isDescendant(List<Category> allCategories, int rootId, int targetId) {
        List<Integer> childrenIds = allCategories.stream()
                .filter(c -> c.parentId() != null && c.parentId() == rootId)
                .map(Category::id)
                .toList();

        for (int childId : childrenIds) {
            if (childId == targetId || isDescendant(allCategories, childId, targetId)) {
                return true;
            }
        }
        return false;
    }

    private void updateDescendantsDepth(List<Category> allCategories, int rootId, int depthDiff) {
        List<Category> children = allCategories.stream()
                .filter(c -> c.parentId() != null && c.parentId() == rootId)
                .toList();

        for (Category child : children) {
            int newDepth = child.depth() + depthDiff;
            categoryMapper.updateParentId(child.id(), child.parentId(), newDepth);
            updateDescendantsDepth(allCategories, child.id(), depthDiff);
        }
    }

    /**
     * 카테고리 순서 변경 도메인 로직
     *
     */
    @Transactional
    public PatchCategorySortOrderResponse patchCategorySortOrder(PatchCategorySortOrderRequest request) {
        for (var item : request.categories()) {
            categoryMapper.updateSortOrder(item.id().longValue(), item.sortOrder());
        }
        return new PatchCategorySortOrderResponse("카테고리 순서가 변경되었습니다.");
    }

    /**
     * 카테고리 삭제 도메인 로직
     *
     */
    @Transactional
    public DeleteCategoryResponse deleteCategory(Integer id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id));

        categoryMapper.delete(id.longValue());
        return new DeleteCategoryResponse("카테고리가 삭제되었습니다.");
    }
}