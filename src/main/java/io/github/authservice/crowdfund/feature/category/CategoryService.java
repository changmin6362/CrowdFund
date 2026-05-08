package io.github.authservice.crowdfund.feature.category;


import io.github.authservice.crowdfund.feature.category.request.CategoryCreateRequest;
import io.github.authservice.crowdfund.feature.category.request.CategoryReorderRequest;
import io.github.authservice.crowdfund.feature.category.response.CategoryResponse;
import io.github.authservice.crowdfund.feature.category.response.CategoryTreeResponse;
import io.github.authservice.crowdfund.feature.category.request.CategoryNameRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createCategory(CategoryCreateRequest request) {
        // 도메인 객체 생성 (Setter나 생성자 사용)
        Category category = new Category(request.name(), request.parentId(), request.level(), request.sortOrder());
        categoryMapper.insertCategory(category); // DB 저장 후 category 객체에 ID가 채워짐
        return convertToResponse(category);
    }

    public List<CategoryTreeResponse> findCategoryTree() {
        List<Category> allCategories = categoryMapper.findAllActiveCategories();
        // 메모리 상에서 트리 구조로 조립하는 로직이 필요합니다.
        return buildTree(allCategories, null);
    }

    // 트리 조립 로직 (재귀)
    private List<CategoryTreeResponse> buildTree(List<Category> all, Long parentId) {
        return all.stream()
                .filter(c -> Objects.equals(c.getParentId(), parentId))
                .map(c -> new CategoryTreeResponse(
                        c.getId(), c.getName(), c.getLevel(), c.getSortOrder(),
                        buildTree(all, c.getId()) // 자식들 재귀 호출
                )).toList();
    }

    // ... 나머지 updateName, delete 등에서도 categoryMapper의 메서드를 호출하도록 수정
}
