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
     * @param request   댓글 작성 요청 데이터
     * @return message, commentId 메시지, 댓글 아이디
     */
    @PostMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCommentResponse createComment(
            @PathVariable Long projectId,
            @RequestBody @Valid CreateCommentRequest request) {

        return service.createComment(projectId, request);
    }

    /**
     * 댓글 수정
     *
     * @param commentId 댓글 아이디
     * @param request   댓글 수정 요청 데이터
     * @return message, commentId 메시지, 댓글 아이디
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
     * @return message, commentList
     */
    @GetMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public GetCommentsResponse getComments(
            @PathVariable Long projectId) {

        return service.getComments(projectId);
    }

    /**
     * 내 댓글 목록 조회
     *
     * @return message, commentList
     */
    @GetMapping("/users/me/comments")
    @ResponseStatus(HttpStatus.OK)
    public GetMyCommentsResponse getMyComments() {

        return service.getMyComments();
    }

    /**
     * 내 댓글 삭제
     *
     * @param commentId 댓글 아이디
     * @return message, commentId 메시지, 댓글 아이디
     */
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteMyCommentResponse deleteMyComment(
            @PathVariable Long commentId) {

        return service.deleteMyComment(commentId);
    }
}