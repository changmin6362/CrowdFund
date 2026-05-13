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
     * 새로운 카테고리를 등록합니다.
     *
     * @param request 등록할 카테고리 정보 (이름, 상위 카테고리 등)
     * @return 등록 완료된 카테고리 상세 정보
     */

    // 1. 카테고리 생성 (201 Created)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCategoryResponse.CategoryInfo createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }

    /**
     * 전체 카테고리 목록을 계층 구조(트리 형태)로 한꺼번에 가져옵니다.
     *
     * @return 상위부터 하위까지 연결된 전체 카테고리 목록
     */
    // 2. 카테고리 트리 조회 (200 OK)
    @GetMapping("/tree")
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryTreeResponse.CategoryTree> findCategoryTree() {
        return categoryService.findCategoryTree();
    }

    /**
     * 특정 카테고리의 이름을 변경합니다.
     *
     * @param id 수정하고 싶은 카테고리 번호
     * @param request 새로 바꿀 이름 정보
     * @return 이름이 수정된 후의 카테고리 정보
     */
    // 3. 이름 수정 (200 OK)
    @PatchMapping("/{id}/name")
    public CreateCategoryResponse.CategoryInfo updateName(@PathVariable Long id, @Valid @RequestBody CategoryNameRequest request) {
        return categoryService.updateName(id, request);
    }

    /**
     * 같은 위치(레벨)에 있는 카테고리들끼리의 보여지는 순서를 바꿉니다.
     *
     * @param request 순서를 어떻게 바꿀지에 대한 목록 정보
     */
    // 4. 순서 변경 (204 No Content)
    @PatchMapping("/reorder")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reorder(@Valid @RequestBody CategoryReorderRequest request) {
        categoryService.reorder(request);
    }

    /**
     * 카테고리를 삭제합니다.
     *
     * @param id 삭제할 카테고리 번호
     */
    // 5. 삭제 (204 No Content)
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
