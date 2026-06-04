package io.github.crowdfund.feature.category.admin;

import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.domain.category.mapper.CategoryMapper;
import io.github.crowdfund.feature.category.admin.dto.active.AdminCategoryActiveRequest;
import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateRequest;
import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateResponse;
import io.github.crowdfund.feature.category.admin.dto.move.AdminCategoryMoveRequest;
import io.github.crowdfund.feature.category.admin.dto.rename.AdminCategoryRenameRequest;
import io.github.crowdfund.feature.category.admin.dto.reorder.AdminCategoryReorderRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminCategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final io.github.crowdfund.feature.category.user.UserCategoryService userCategoryService;

    /**
     * 카테고리 생성 도메인 로직
     */
    @Transactional
    public AdminCategoryCreateResponse create(@Valid AdminCategoryCreateRequest request) {

        // 카테고리의 깊이 계산 (0부터 시작)
        int depth = 0;
        if (request.parentId() != null) {
            Category parent = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));
            depth = parent.depth() + 1;
        }

        // 카테고리의 정렬 순서 계산 (마지막 순서 + 10)
        int sortOrder = 10;
        List<Category> siblings = categoryRepository.findByParentIdAndIsActiveTrueOrderBySortOrderAsc(request.parentId());
        if (!siblings.isEmpty()) {
            sortOrder = siblings.stream()
                    .mapToInt(Category::sortOrder)
                    .max()
                    .orElse(0) + 10;
        }

        CategoryMapper.CategoryInsertResult result = new CategoryMapper.CategoryInsertResult();
        categoryMapper.insert(
                request,
                depth,
                sortOrder,
                result
        );

        return new AdminCategoryCreateResponse(userCategoryService.fetch().categoryTree());
    }

    /**
     * 카테고리 이름 변경 도메인 로직
     */
    @Transactional
    public io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse rename(Integer id, AdminCategoryRenameRequest request) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("변경하려는 카테고리를 찾을 수 없습니다."));

        categoryMapper.updateName(id.longValue(), request.name());

        return userCategoryService.fetch();
    }

    /**
     * 카테고리 부모 변경 도메인 로직
     */
    @Transactional
    public io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse move(Integer categoryId, AdminCategoryMoveRequest request) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("변경하려는 카테고리를 찾을 수 없습니다."));

        // 자기 자신을 부모로 설정하는지 체크
        if (request.parentId() != null && request.parentId().equals(categoryId)) {
            throw new IllegalArgumentException("자기 자신을 부모로 설정할 수 없습니다.");
        }

        int newDepth = 0;
        if (request.parentId() != null) {
            Category parent = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new IllegalArgumentException("부모 카테고리를 찾을 수 없습니다."));
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
                    throw new IllegalArgumentException("자식 카테고리를 부모로 설정할 수 없습니다. (순환 참조 방지)");
                }
            }

            // 새로운 정렬 순서 계산 (새로운 부모 밑에서의 마지막 순서 + 10)
            int newSortOrder = 10;
            List<Category> siblings = categoryRepository.findByParentIdAndIsActiveTrueOrderBySortOrderAsc(request.parentId());
            if (!siblings.isEmpty()) {
                newSortOrder = siblings.stream()
                        .mapToInt(Category::sortOrder)
                        .max()
                        .orElse(0) + 10;
            }

            // 깊이 차이 계산
            int depthDiff = newDepth - category.depth();

            // 상위 카테고리 업데이트 (부모, 깊이, 정렬 순서)
            categoryMapper.updateParentId(categoryId, request.parentId(), newDepth, newSortOrder);

            // 하위 카테고리들의 깊이 일괄 업데이트
            if (depthDiff != 0) {
                updateDescendantsDepth(allCategories, categoryId, depthDiff);
            }
        }

        return userCategoryService.fetch();
    }

    /**
     * 카테고리 정렬 순서 변경 도메인 로직
     */
    @Transactional
    public io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse reorder(AdminCategoryReorderRequest request) {
        for (var item : request.categories()) {
            categoryMapper.updateSortOrder(item.categoryId().longValue(), item.sortOrder());
        }

        return userCategoryService.fetch();
    }

    /**
     * 카테고리 활성 여부 변경 도메인 로직
     */
    @Transactional
    public io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse toggle(Integer id, AdminCategoryActiveRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("변경하려는 카테고리를 찾을 수 없습니다."));

        if (category.isActive() == request.isActive()) {
            throw new IllegalArgumentException("이미 해당 활성 상태입니다.");
        }

        categoryMapper.updateActiveStatus(id, request.isActive());

        return userCategoryService.fetch();
    }

    /**
     * 카테고리 삭제 도메인 로직
     */
    @Transactional
    public io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse delete(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("삭제하려는 카테고리를 찾을 수 없습니다."));

        if (!category.isActive()) {
            throw new IllegalArgumentException("카테고리가 이미 삭제되었습니다.");
        }

        categoryMapper.delete(id.longValue());

        return userCategoryService.fetch();
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
            categoryMapper.updateParentId(child.id(), child.parentId(), newDepth, child.sortOrder());
            updateDescendantsDepth(allCategories, child.id(), depthDiff);
        }
    }
}