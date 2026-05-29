package io.github.crowdfund.feature.comment.my;

import io.github.crowdfund.feature.comment.my.dto.fetch.MyCommentsResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MyCommentController {

    private final MyCommentService service;

    /**
     * 내 댓글 목록 조회
     *
     * @return message, myComments
     */
    @Operation(summary = "내 댓글 목록 조회")
    @GetMapping("/users/me/comments/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<MyCommentsResponse> fetch(@PathVariable Long userId) {

        return ApiResult.success("내 댓글 목록 조회에 성공했습니다.", service.fetch(userId));
    }
}
