package io.github.authservice.crowdfund.feature.category;

import io.github.authservice.crowdfund.feature.category.request.CategoryNameRequest;
import io.github.authservice.crowdfund.feature.category.request.CreateCategoryRequest;
import io.github.authservice.crowdfund.feature.category.request.PatchCategoryParentRequest;
import io.github.authservice.crowdfund.feature.category.request.PatchCategorySortOrderRequest;
import io.github.authservice.crowdfund.feature.category.response.CreateCategoryResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService service;

    /**
     * 카테고리 생성
     *
     * @param request 등록할 카테고리 정보
     * @return message, category
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CreateCategoryResponse> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        return ApiResult.success("카테고리 생성에 성공했습니다.", service.createCategory(request));
    }

    /**
     * 카테고리 이름 변경
     *
     * @param categoryId 카테고리 ID
     * @param request    새로 바꿀 이름 정보
     * @return message
     */
    @PatchMapping("/{categoryId}/name")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> patchCategoryName(@PathVariable Integer categoryId, @Valid @RequestBody CategoryNameRequest request) {
        service.patchCategoryName(categoryId, request);

        return ApiResult.success("카테고리 이름 변경에 성공했습니다.");
    }

    /**
     * 카테고리 부모 변경
     *
     * @param categoryId 카테고리 ID
     * @param request    부모 카테고리 ID
     * @return message
     */
    @PatchMapping("/{categoryId}/parent")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> patchCategoryParent(@PathVariable Integer categoryId, @RequestBody PatchCategoryParentRequest request) {
        service.patchCategoryParent(categoryId, request);

        return ApiResult.success("카테고리 부모 변경에 성공했습니다.");
    }

    /**
     * 카테고리 정렬 순서 변경
     *
     * @param request 변경할 카테고리 정보
     * @return message
     */
    @PatchMapping("/sort-order")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> patchCategorySortOrder(@Valid @RequestBody PatchCategorySortOrderRequest request) {
        service.patchCategorySortOrder(request);

        return ApiResult.success("카테고리 정렬 순서 변경에 성공했습니다.");
    }

    /**
     * 카테고리 삭제
     *
     * @param categoryId 카테고리 ID
     */
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> deleteCategory(@PathVariable Integer categoryId) {
        service.deleteCategory(categoryId);

        return ApiResult.success("카테고리 삭제에 성공했습니다.");
    }
}
