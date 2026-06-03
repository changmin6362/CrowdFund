package io.github.crowdfund.feature.category.admin;

import io.github.crowdfund.feature.category.admin.dto.active.AdminCategoryActiveRequest;
import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateRequest;
import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateResponse;
import io.github.crowdfund.feature.category.admin.dto.move.AdminCategoryMoveRequest;
import io.github.crowdfund.feature.category.admin.dto.rename.AdminCategoryRenameRequest;
import io.github.crowdfund.feature.category.admin.dto.reorder.AdminCategoryReorderRequest;
import io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "Category - Admin", description = "관리자용 카테고리 API")
public class AdminCategoryController {

    private final AdminCategoryService service;

    /**
     * 카테고리 생성
     *
     * @param request 등록할 카테고리 정보
     * @return message, category, categoryTree
     */
    @Operation(summary = "카테고리 생성", description = "생성된 카테고리 정보와 전체 트리를 반환합니다.")
    @ApiResponse(responseCode = "201", description = "카테고리 생성 성공 응답 예시")
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
     * @return message, categoryTree
     */
    @Operation(summary = "카테고리 이름 변경")
    @ApiResponse(responseCode = "200", description = "카테고리 이름 변경 성공 응답 예시")
    @PatchMapping("/{categoryId}/rename")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchCategoryResponse> rename(@PathVariable Integer categoryId, @Valid @RequestBody AdminCategoryRenameRequest request) {

        return ApiResult.success("카테고리 이름 변경에 성공했습니다.", service.rename(categoryId, request));
    }

    /**
     * 카테고리 부모 변경
     *
     * @param categoryId 카테고리 ID
     * @param request    부모 카테고리 ID
     * @return message, categoryTree
     */
    @Operation(summary = "카테고리 부모 변경")
    @ApiResponse(responseCode = "200", description = "카테고리 부모 변경 성공 응답 예시")
    @PatchMapping("/{categoryId}/parent")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchCategoryResponse> move(@PathVariable Integer categoryId, @Valid @RequestBody AdminCategoryMoveRequest request) {

        return ApiResult.success("카테고리 부모 변경에 성공했습니다.", service.move(categoryId, request));
    }

    /**
     * 카테고리 정렬 순서 변경
     *
     * @param request 변경할 카테고리 정보
     * @return message, categoryTree
     */
    @Operation(summary = "카테고리 정렬 순서 변경")
    @ApiResponse(responseCode = "200", description = "카테고리 정렬 순서 변경 성공 응답 예시")
    @PatchMapping("/sort-order")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchCategoryResponse> reorder(@Valid @RequestBody AdminCategoryReorderRequest request) {

        return ApiResult.success("카테고리 정렬 순서 변경에 성공했습니다.", service.reorder(request));
    }

    /**
     * 카테고리 활성 여부 변경
     *
     * @param categoryId 카테고리 ID
     * @param request    활성 여부 정보
     * @return message, categoryTree
     */
    @Operation(summary = "카테고리 활성 여부 변경")
    @ApiResponse(responseCode = "200", description = "카테고리 활성 여부 변경 성공 응답 예시")
    @PatchMapping("/{categoryId}/toggle")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchCategoryResponse> toggle(@PathVariable Integer categoryId, @Valid @RequestBody AdminCategoryActiveRequest request) {

        return ApiResult.success("카테고리 활성 상태 변경에 성공했습니다.", service.toggle(categoryId, request));
    }

    /**
     * 카테고리 삭제
     *
     * @param categoryId 카테고리 ID
     * @return message, categoryTree
     */
    @Operation(summary = "카테고리 삭제")
    @ApiResponse(responseCode = "200", description = "카테고리 삭제 성공 응답 예시")
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchCategoryResponse> delete(@PathVariable Integer categoryId) {

        return ApiResult.success("카테고리 삭제에 성공했습니다.", service.delete(categoryId));
    }
}
