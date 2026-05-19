package io.github.authservice.crowdfund.feature.category;

import io.github.authservice.crowdfund.domain.category.Category;
import io.github.authservice.crowdfund.domain.category.CategoryMapper;
import io.github.authservice.crowdfund.feature.category.request.CategoryNameRequest;
import io.github.authservice.crowdfund.feature.category.request.CategoryReorderRequest;
import io.github.authservice.crowdfund.feature.category.request.CreateCategoryRequest;
import io.github.authservice.crowdfund.feature.category.response.CategoryTreeResponse;
import io.github.authservice.crowdfund.feature.category.response.CreateCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 생성 도메인 로직
     *
     * @param request
     * @return
     */
    @Transactional
    public CreateCategoryResponse.CategoryInfo createCategory(@Valid CreateCategoryRequest request) {
        // 빌더를 사용하여 모델(도메인) 객체 생성
//        Category category = Category.builder()
//                .name(request.name())
//                .parentId(Long.valueOf(request.parentId()))
//                .level(request.level())
//                .sortOrder(request.sortOrder())
//                .isActive(true)
//                .build();

//        categoryMapper.insert(category); // DB 저장 (useGeneratedKeys에 의해 id가 채워짐)

//        return convertToResponse(category);
        return null;
    }

    /**
     * 카테고리 트리 조회 도메인 로직
     *
     * @return
     */
    public List<CategoryTreeResponse.CategoryTree> findCategoryTree() {
        // 1. DB에서 활성화된 모든 카테고리를 평면적으로 가져옴
//        List<Category> allCategories = categoryMapper.findAllActive();

        // 2. 최상위 카테고리(parentId가 null)를 시작으로 트리 조립
//        return buildTree(allCategories, null);
        return null;
    }

    /**
     * 카테고리 이름 수정 도메인 로직
     *
     * @param id
     * @param request
     * @return
     */
    @Transactional
    public CreateCategoryResponse.CategoryInfo updateName(Long id, CategoryNameRequest request) {
//        categoryMapper.updateName(id, request.name());

//        Category updated = categoryMapper.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없습니다. ID: " + id));

//        return convertToResponse(updated);
        return null;
    }

    /**
     * 카테고리 순서 변경 도메인 로직
     *
     * @param request
     */
    @Transactional
    public void reorder(CategoryReorderRequest request) {
//        for (var orderUpdate : request.orders()) {
//            categoryMapper.updateSortOrder(orderUpdate.id(), orderUpdate.sortOrder());
//        }
    }

    /**
     * 카테고리 삭제 도메인 로직
     *
     * @param id
     */
    @Transactional
    public void delete(Long id) {
//        categoryMapper.delete(id);
    }

    // --- 내부 유틸리티 메서드 ---

    // 트리 구조 조립을 위한 재귀 메서드
    private List<CategoryTreeResponse.CategoryTree> buildTree(List<Category> all, Long parentId) {
//        return all.stream()
//                .filter(c -> Objects.equals(c.getParentId(), parentId))
//                .map(c -> new CategoryTreeResponse.CategoryTree(
//                        c.getId(),
//                        c.getName(),
//                        c.getSortOrder(),
//                        c.getLevel(),
//                        buildTree(all, c.getId()) // 자식들 다시 찾기 (재귀)
//                ))
//                .toList();
        return null;
    }

    private CreateCategoryResponse.CategoryInfo convertToResponse(Category category) {
//        return new CreateCategoryResponse.CategoryInfo(
//                category.getId(),
//                category.getName(),
//                category.getParentId(),
//                category.getSortOrder(),
//                category.getLevel(),
//                category.isActive()
//        );
        return null;
    }
}