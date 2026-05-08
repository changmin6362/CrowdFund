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
     * dsdsd
     * @param request ssds
     * @return message
     */
    @PostMapping // 카테고리 생성
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse createCategory(@Valid @RequestBody CategoryCreateRequest request) {
        return categoryService.createCategory(request);
    }

    /**
     *
     * @return
     */
    @GetMapping("/tree") // 카테고리 트리 조회
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public List<CategoryTreeResponse> findCategoryTree() {
        return categoryService.findCategoryTree();
    }

    @PatchMapping("/{categoryId}") // 카테고리 이름 변경
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CategoryResponse updateCategoryName(
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryNameRequest request) {
        return categoryService.updateCategoryName(categoryId, request);
    }

    @PatchMapping("/reorder") //카테고리 순서 변경
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reorderCategories(@Valid @RequestBody CategoryReorderRequest request) {
        categoryService.reorderCategories(request);
    }

    @DeleteMapping("/{categoryId}") // 카테고리 삭제
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long categoryId) {
        categoryService.deleteCategory(categoryId);
    }


}
