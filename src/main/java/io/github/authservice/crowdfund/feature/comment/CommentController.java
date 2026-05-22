package io.github.authservice.crowdfund.feature.comment;

import io.github.authservice.crowdfund.feature.comment.request.CommentAddRequest;
import io.github.authservice.crowdfund.feature.comment.request.CommentModifyRequest;
import io.github.authservice.crowdfund.feature.comment.response.CommentAddResponse;
import io.github.authservice.crowdfund.feature.comment.response.CommentDeleteResponse;
import io.github.authservice.crowdfund.feature.comment.response.CommentListResponse;
import io.github.authservice.crowdfund.feature.comment.response.CommentModifyResponse;
import io.github.authservice.crowdfund.feature.comment.response.MyCommentListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     *
     * @param projectId 프로젝트 아이디
     * @param request   댓글 작성 요청 데이터
     * @return message, commentId 메시지, 댓글 아이디
     */
    @PostMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentAddResponse addComment(
            @PathVariable Long projectId,
            @RequestBody @Valid CommentAddRequest request) {

        return commentService.addComment(projectId, request);
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
    public CommentModifyResponse modifyComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentModifyRequest request) {

        return commentService.modifyComment(commentId, request);
    }

    /**
     * 프로젝트 댓글 목록 조회
     *
     * @param projectId 프로젝트 아이디
     * @return message, commentList
     */
    @GetMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public CommentListResponse getComments(
            @PathVariable Long projectId) {

        return commentService.getComments(projectId);
    }

    /**
     * 내 댓글 목록 조회
     *
     * @return message, commentList
     */
    @GetMapping("/users/me/comments")
    @ResponseStatus(HttpStatus.OK)
    public MyCommentListResponse getMyComments() {

        return commentService.getMyComments();
    }

    /**
     * 내 댓글 삭제
     *
     * @param commentId 댓글 아이디
     * @return message, commentId 메시지, 댓글 아이디
     */
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDeleteResponse deleteMyComment(
            @PathVariable Long commentId) {

        return commentService.deleteMyComment(commentId);
    }
}