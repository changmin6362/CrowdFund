package io.github.crowdfund.feature.comment.my;

import io.github.crowdfund.feature.comment.my.dto.fetch.MyCommentsResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.github.crowdfund.global.security.SecurityUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
@Tag(name = "Comment - My", description = "내 댓글 API")
public class MyCommentController {

    private final MyCommentService service;

    /**
     * 내 댓글 목록 조회 (복합 커서 기반 최신순 페이지네이션)
     *
     * @return message, myComments
     */
    @Operation(summary = "내 댓글 목록 조회")
    @ApiResponse(responseCode = "200", description = "내 댓글 목록 조회 성공 응답 예시")
    @GetMapping("/users/me/comments")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<MyCommentsResponse> fetch(
            @AuthenticationPrincipal SecurityUser securityUser,
            @ParameterObject CursorRequest cursorRequest,
            @RequestParam(defaultValue = "10") @Positive Integer limit) {

        return ApiResult.success("내 댓글 목록 조회에 성공했습니다.", service.fetch(securityUser.getUserId(), cursorRequest, limit));
    }
}
