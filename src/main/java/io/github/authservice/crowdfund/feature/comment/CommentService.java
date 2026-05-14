package io.github.authservice.crowdfund.feature.comment;

import io.github.authservice.crowdfund.domain.comment.CommentRepository;
import io.github.authservice.crowdfund.feature.comment.request.CommentAddRequest;
import io.github.authservice.crowdfund.feature.comment.request.CommentModifyRequest;
import io.github.authservice.crowdfund.feature.comment.response.CommentAddResponse;
import io.github.authservice.crowdfund.feature.comment.response.CommentDeleteResponse;
import io.github.authservice.crowdfund.feature.comment.response.CommentListResponse;
import io.github.authservice.crowdfund.feature.comment.response.CommentModifyResponse;
import io.github.authservice.crowdfund.feature.comment.response.MyCommentListResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 댓글 서비스
 * - 댓글 작성
 * - 댓글 수정
 * - 프로젝트 댓글 목록 조회
 * - 내 댓글 목록 조회
 * - 내 댓글 삭제
 */
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    /**
     * 댓글 작성 성공 응답 반환
     *
     * @param projectId 댓글을 작성할 프로젝트 ID
     * @param request 댓글 작성 요청 데이터
     * @return message, comment
     */
    public CommentAddResponse addComment(Long projectId, @Valid CommentAddRequest request) {
        // return new CommentAddResponse(
        //         "댓글 작성이 성공적으로 완료되었습니다",
        //         repository.addComment(projectId, request)
        // );
        return new CommentAddResponse("댓글 작성 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 댓글 수정 성공 응답 반환
     *
     * @param commentId 수정할 댓글 ID
     * @param request 댓글 수정 요청 데이터
     * @return message, comment
     */
    public CommentModifyResponse modifyComment(Long commentId, @Valid CommentModifyRequest request) {
        // return new CommentModifyResponse(
        //         "댓글 수정이 성공적으로 완료되었습니다",
        //         repository.modifyComment(commentId, request)
        // );
        return new CommentModifyResponse("댓글 수정 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 프로젝트 댓글 목록 조회 성공 응답 반환
     *
     * @param projectId 댓글 목록을 조회할 프로젝트 ID
     * @return message, commentList
     */
    public CommentListResponse getComments(Long projectId) {
        // return new CommentListResponse(
        //         "댓글 목록 조회가 성공적으로 완료되었습니다",
        //         repository.getComments(projectId)
        // );
        return new CommentListResponse("댓글 목록 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 내 댓글 목록 조회 성공 응답 반환
     *
     * @return message, commentList
     */
    public MyCommentListResponse getMyComments() {
        // return new MyCommentListResponse(
        //         "내 댓글 목록 조회가 성공적으로 완료되었습니다",
        //         repository.getMyComments()
        // );
        return new MyCommentListResponse("내 댓글 목록 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 내 댓글 삭제 성공 응답 반환
     *
     * @param commentId 삭제할 댓글 ID
     * @return message, comment
     */
    public CommentDeleteResponse deleteMyComment(Long commentId) {
        // return new CommentDeleteResponse(
        //         "내 댓글 삭제가 성공적으로 완료되었습니다",
        //         repository.deleteMyComment(commentId)
        // );
        return new CommentDeleteResponse("내 댓글 삭제 기능은 구현되지 않았습니다.");
    }
}