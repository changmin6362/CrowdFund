package io.github.crowdfund.feature.category.user;

import io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse;
import io.github.crowdfund.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class UserCategoryController {

    private final UserCategoryService service;

    /**
     * 카테고리 트리 조회
     *
     * @return message, categoryTree
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserFetchCategoryResponse> fetch() {
        return ApiResult.success("카테고리 트리 조회에 성공했습니다.", service.fetch());
    }
}
