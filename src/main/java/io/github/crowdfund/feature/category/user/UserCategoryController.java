package io.github.crowdfund.feature.category.user;

import io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Validated
@Tag(name = "04. Category - User", description = "사용자용 카테고리 API")
public class UserCategoryController {

    private final UserCategoryService service;

    /**
     * 카테고리 트리 조회
     *
     * @return message, categoryTree
     */
    @Operation(summary = "카테고리 트리 조회")
    @ApiResponse(responseCode = "200", description = "카테고리 트리 조회 성공 응답 예시")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchCategoryResponse> fetch() {
        return ApiResult.success("카테고리 트리 조회에 성공했습니다.", service.fetch());
    }
}
