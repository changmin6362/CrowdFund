package io.github.authservice.crowdfund.feature.comment;

import io.github.authservice.crowdfund.domain.comment.CommentRepository;
import io.github.authservice.crowdfund.feature.comment.request.CreateCommentRequest;
import io.github.authservice.crowdfund.feature.comment.request.PatchCommentRequest;
import io.github.authservice.crowdfund.feature.comment.response.CreateCommentResponse;
import io.github.authservice.crowdfund.feature.comment.response.DeleteMyCommentResponse;
import io.github.authservice.crowdfund.feature.comment.response.GetCommentsResponse;
import io.github.authservice.crowdfund.feature.comment.response.PatchCommentResponse;
import io.github.authservice.crowdfund.feature.comment.response.GetMyCommentsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository repository;

    /**
     * 댓글 작성 도메인 로직
     */
    public CreateCommentResponse createComment(Long projectId, Long userId, @Valid CreateCommentRequest request) {
        // return new CommentAddResponse(
        //         "댓글 작성이 성공적으로 완료되었습니다",
        //         repository.addComment(projectId, request)
        // );
        return new CreateCommentResponse("댓글 작성 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 댓글 수정 도메인 로직
     */
    public PatchCommentResponse patchComment(Long commentId, @Valid PatchCommentRequest request) {
        // return new CommentModifyResponse(
        //         "댓글 수정이 성공적으로 완료되었습니다",
        //         repository.modifyComment(commentId, request)
        // );
        return new PatchCommentResponse("댓글 수정 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 프로젝트 댓글 목록 조회 도메인 로직
     */
    public GetCommentsResponse getComments(Long projectId) {
        // return new CommentListResponse(
        //         "댓글 목록 조회가 성공적으로 완료되었습니다",
        //         repository.getComments(projectId)
        // );
        return new GetCommentsResponse("댓글 목록 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 내 댓글 목록 조회 도메인 로직
     */
    public GetMyCommentsResponse getMyComments() {
        // return new MyCommentListResponse(
        //         "내 댓글 목록 조회가 성공적으로 완료되었습니다",
        //         repository.getMyComments()
        // );
        return new GetMyCommentsResponse("내 댓글 목록 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 내 댓글 삭제 도메인 로직
     */
    public DeleteMyCommentResponse deleteMyComment(Long commentId) {
        // return new CommentDeleteResponse(
        //         "내 댓글 삭제가 성공적으로 완료되었습니다",
        //         repository.deleteMyComment(commentId)
        // );
        return new DeleteMyCommentResponse("내 댓글 삭제 기능은 구현되지 않았습니다.", null);
    }
}