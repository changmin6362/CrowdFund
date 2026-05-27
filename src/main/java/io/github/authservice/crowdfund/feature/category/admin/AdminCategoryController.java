package io.github.authservice.crowdfund.feature.category.admin;

import io.github.authservice.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateRequest;
import io.github.authservice.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateResponse;
import io.github.authservice.crowdfund.feature.category.admin.dto.move.AdminCategoryMoveRequest;
import io.github.authservice.crowdfund.feature.category.admin.dto.rename.AdminCategoryRenameRequest;
import io.github.authservice.crowdfund.feature.category.admin.dto.reorder.AdminCategoryReorderRequest;
import io.github.authservice.crowdfund.global.common.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final AdminCategoryService service;

    /**
     * 카테고리 생성
     *
     * @param request 등록할 카테고리 정보
     * @return message, category
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<AdminCategoryCreateResponse> create(@Valid @RequestBody AdminCategoryCreateRequest request) {
        return ApiResult.success("카테고리 생성에 성공했습니다.", service.create(request));
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
    public ApiResult<Void> rename(@PathVariable Integer categoryId, @Valid @RequestBody AdminCategoryRenameRequest request) {
        service.rename(categoryId, request);

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
    public ApiResult<Void> move(@PathVariable Integer categoryId, @RequestBody AdminCategoryMoveRequest request) {
        service.move(categoryId, request);

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
    public ApiResult<Void> reorder(@Valid @RequestBody AdminCategoryReorderRequest request) {
        service.reorder(request);

        return ApiResult.success("카테고리 정렬 순서 변경에 성공했습니다.");
    }

    /**
     * 카테고리 삭제
     *
     * @param categoryId 카테고리 ID
     */
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> delete(@PathVariable Integer categoryId) {
        service.delete(categoryId);

        return ApiResult.success("카테고리 삭제에 성공했습니다.");
    }
}
