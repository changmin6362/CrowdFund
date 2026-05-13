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

/**
 * 댓글 관련 API 컨트롤러
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     * - 대상: 프로젝트
     * - projectId : PathVariable
     * - 댓글 내용 : RequestBody
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
     * - 대상: 댓글
     * - commentId : PathVariable
     * - 수정 내용 : RequestBody
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
     * - 대상: 프로젝트
     * - projectId : PathVariable
     * - RequestBody 없음
     */
    @GetMapping("/projects/{projectId}/comments")
    @ResponseStatus(HttpStatus.OK)
    public CommentListResponse getComments(
            @PathVariable Long projectId) {

        return commentService.getComments(projectId);
    }

    /**
     * 내 댓글 목록 조회
     * - 로그인 사용자 기준 조회
     * - PathVariable 없음
     * - RequestBody 없음
     */
    @GetMapping("/users/me/comments")
    @ResponseStatus(HttpStatus.OK)
    public MyCommentListResponse getMyComments() {

        return commentService.getMyComments();
    }

    /**
     * 내 댓글 삭제
     * - 대상: 현재 로그인한 사용자의 댓글
     * - commentId : PathVariable
     * - RequestBody 없음
     */
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDeleteResponse deleteMyComment(
            @PathVariable Long commentId) {

        return commentService.deleteMyComment(commentId);
    }
}