package io.github.authservice.crowdfund.feature.comment;

import io.github.authservice.crowdfund.feature.comment.request.CreateCommentRequest;
import io.github.authservice.crowdfund.feature.comment.request.PatchCommentRequest;
import io.github.authservice.crowdfund.feature.comment.response.CreateCommentResponse;
import io.github.authservice.crowdfund.feature.comment.response.DeleteMyCommentResponse;
import io.github.authservice.crowdfund.feature.comment.response.GetCommentsResponse;
import io.github.authservice.crowdfund.feature.comment.response.PatchCommentResponse;
import io.github.authservice.crowdfund.feature.comment.response.GetMyCommentsResponse;
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
    public CreateCommentResponse createComment(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @RequestBody @Valid CreateCommentRequest request) {

        return service.createComment(projectId, userId, request);
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
    public PatchCommentResponse patchComment(
            @PathVariable Long commentId,
            @RequestBody @Valid PatchCommentRequest request) {

        return service.patchComment(commentId, request);
    }

    /**
     * 프로젝트 댓글 목록 조회
     *
     * @param projectId 프로젝트 아이디
     * @return message, comments
     */
    @GetMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public GetCommentsResponse getComments(
            @PathVariable Long projectId,
            @RequestParam(required = false) Long currentUserId) {

        return service.getComments(projectId, currentUserId);
    }

    /**
     * 내 댓글 목록 조회
     *
     * @return message, myComments
     */
    @GetMapping("/users/me/comments/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public GetMyCommentsResponse getMyComments(@PathVariable Long userId) {

        return service.getMyComments(userId);
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
    public DeleteMyCommentResponse deleteMyComment(
            @PathVariable Long commentId,
            @PathVariable Long userId) {

        return service.deleteMyComment(commentId, userId);
    }
}