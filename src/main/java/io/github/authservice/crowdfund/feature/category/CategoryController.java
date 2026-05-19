package io.github.authservice.crowdfund.feature.category;

import io.github.authservice.crowdfund.feature.category.request.CategoryNameRequest;
import io.github.authservice.crowdfund.feature.category.request.CategoryReorderRequest;
import io.github.authservice.crowdfund.feature.category.request.CreateCategoryRequest;
import io.github.authservice.crowdfund.feature.category.response.CategoryTreeResponse;
import io.github.authservice.crowdfund.feature.category.response.CreateCategoryResponse;
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
     * 카테고리 생성
     *
     * @param request 등록할 카테고리 정보
     * @return 등록 완료된 카테고리 상세 정보
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCategoryResponse.CategoryInfo createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }

    /**
     * 카테고리 트리 조회
     *
     * @return 상위부터 하위까지 연결된 전체 카테고리 목록
     */
    @GetMapping("/tree")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryTreeResponse.CategoryTree> findCategoryTree() {
        return categoryService.findCategoryTree();
    }

    /**
     * 카테고리 이름 변경
     *
     * @param id 카테고리 ID
     * @param request 새로 바꿀 이름 정보
     * @return 이름이 수정된 후의 카테고리 정보
     */
    @PatchMapping("/{id}/name")
    @ResponseStatus(HttpStatus.OK)
    public CreateCategoryResponse.CategoryInfo updateName(@PathVariable Long id, @Valid @RequestBody CategoryNameRequest request) {
        return categoryService.updateName(id, request);
    }

    /**
     * 카테고리 순서 변경
     *
     * @param request 순서를 어떻게 바꿀지에 대한 목록 정보
     */
    @PatchMapping("/reorder")
    @ResponseStatus(HttpStatus.OK)
    public void reorder(@Valid @RequestBody CategoryReorderRequest request) {
        categoryService.reorder(request);
    }

    /**
     * 카테고리 삭제
     *
     * @param id 삭제할 카테고리 번호
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
