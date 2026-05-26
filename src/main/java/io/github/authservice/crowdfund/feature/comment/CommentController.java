package io.github.authservice.crowdfund.feature.comment;

import io.github.authservice.crowdfund.feature.comment.request.CreateCommentRequest;
import io.github.authservice.crowdfund.feature.comment.request.PatchCommentRequest;
import io.github.authservice.crowdfund.feature.comment.response.*;
import io.github.authservice.crowdfund.global.common.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    /**
     * 댓글 작성
     *
     * @param projectId 프로젝트 아이디
     * @param userId    유저 아이디
     * @param request   댓글 작성 요청 데이터
     * @return message, createdComment
     */
    @PostMapping("/projects/{projectId}/comments/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CreateCommentResponse> createComment(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestBody @Valid CreateCommentRequest request) {

        return ApiResult.success("댓글 작성에 성공했습니다.", service.createComment(projectId, userId, request));
    }

    /**
     * 댓글 수정
     *
     * @param commentId 댓글 아이디
     * @param request   댓글 수정 요청 데이터
     * @return message, patchedComment
     */
    @PatchMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PatchCommentResponse> patchComment(
            @PathVariable Long commentId,
            @RequestBody @Valid PatchCommentRequest request) {

        return ApiResult.success("댓글 수정에 성공했습니다.", service.patchComment(commentId, request));
    }

    /**
     * 프로젝트 댓글 목록 조회
     *
     * @param projectId 프로젝트 아이디
     * @return message, comments
     */
    @GetMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetCommentsResponse> getComments(
            @PathVariable Long projectId,
            @RequestParam(required = false) Long currentUserId) {

        return ApiResult.success("댓글 목록 조회에 성공했습니다.", service.getComments(projectId, currentUserId));
    }

    /**
     * 내 댓글 목록 조회
     *
     * @return message, myComments
     */
    @GetMapping("/users/me/comments/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetMyCommentsResponse> getMyComments(@PathVariable Long userId) {

        return ApiResult.success("내 댓글 목록 조회에 성공했습니다.", service.getMyComments(userId));
    }

    /**
     * 내 댓글 삭제
     *
     * @param commentId 댓글 아이디
     * @param userId    유저 아이디
     * @return message, deletedCommentId
     */
    @DeleteMapping("/comments/{commentId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<DeleteMyCommentResponse> deleteMyComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {

        return ApiResult.success("내 댓글 삭제에 성공했습니다.", service.deleteMyComment(commentId, userId));
    }
}