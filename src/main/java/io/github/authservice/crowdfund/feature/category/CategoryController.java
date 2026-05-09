package io.github.authservice.crowdfund.feature.category;

import io.github.authservice.crowdfund.feature.category.request.CategoryCreateRequest;
import io.github.authservice.crowdfund.feature.category.request.CategoryNameRequest;
import io.github.authservice.crowdfund.feature.category.request.CategoryReorderRequest;
import io.github.authservice.crowdfund.feature.category.response.CategoryResponse;
import io.github.authservice.crowdfund.feature.category.response.CategoryTreeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     *
     * @param request
     * @return
     */
    // 1. 카테고리 생성 (201 Created)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        return categoryService.createCategory(request);
    }

    // 2. 카테고리 트리 조회 (200 OK)
    @GetMapping("/tree")
    public List<CategoryTreeResponse> findCategoryTree() {
        return categoryService.findCategoryTree();
    }

    // 3. 이름 수정 (200 OK)
    @PatchMapping("/{id}/name")
    public CategoryResponse updateName(@PathVariable Long id, @Valid @RequestBody CategoryNameRequest request) {
        return categoryService.updateName(id, request);
    }

    // 4. 순서 변경 (204 No Content)
    @PatchMapping("/reorder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reorder(@Valid @RequestBody CategoryReorderRequest request) {
        categoryService.reorder(request);
    }

    // 5. 삭제 (204 No Content)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
