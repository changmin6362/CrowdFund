package io.github.crowdfund.feature.category.admin;

import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateRequest;
import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateResponse;
import io.github.crowdfund.feature.category.admin.dto.move.AdminCategoryMoveRequest;
import io.github.crowdfund.feature.category.admin.dto.rename.AdminCategoryRenameRequest;
import io.github.crowdfund.feature.category.admin.dto.reorder.AdminCategoryReorderRequest;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
@Tag(name = "Admin Category", description = "관리자용 카테고리 관리 API")
public class AdminCategoryController {

    private final AdminCategoryService service;

    /**
     * 카테고리 생성
     *
     * @param request 등록할 카테고리 정보
     * @return message, category
     */
    @Operation(summary = "카테고리 생성", description = "생성된 카테고리 정보를 그대로 반환합니다.")
    @ApiResponse(responseCode = "201", description = "카테고리 생성 성공")
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
    @Operation(summary = "카테고리 이름 변경")
    @ApiResponse(responseCode = "200", description = "카테고리 이름 변경 성공")
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
    @Operation(summary = "카테고리 부모 변경")
    @ApiResponse(responseCode = "200", description = "카테고리 부모 변경 성공")
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
    @Operation(summary = "카테고리 정렬 순서 변경")
    @ApiResponse(responseCode = "200", description = "카테고리 정렬 순서 변경 성공")
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
    @Operation(summary = "카테고리 삭제")
    @ApiResponse(responseCode = "200", description = "카테고리 삭제 성공")
    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> delete(@PathVariable Integer categoryId) {
        service.delete(categoryId);

        return ApiResult.success("카테고리 삭제에 성공했습니다.");
    }
}
