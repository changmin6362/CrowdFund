package io.github.authservice.crowdfund.feature.comment;

import io.github.authservice.crowdfund.domain.comment.Comment;
import io.github.authservice.crowdfund.domain.comment.CommentRepository;
import io.github.authservice.crowdfund.domain.comment.mapper.CommentMapper;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.comment.request.CreateCommentRequest;
import io.github.authservice.crowdfund.feature.comment.request.PatchCommentRequest;
import io.github.authservice.crowdfund.feature.comment.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final CommentMapper mapper;

    /**
     * 댓글 작성 도메인 로직
     */
    @Transactional
    public CreateCommentResponse createComment(Long projectId, Long userId, @Valid CreateCommentRequest request) {

        Comment comment = new Comment(
                null,
                userId,
                projectId,
                request.content(),
                LocalDateTime.now()
        );

        Comment savedComment = repository.save(comment);

        String nickname = userRepository.findById(userId)
                // User에서 nickname을 가져옴
                .map(User::nickname)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        CommentInfo commentInfo = new CommentInfo(
                savedComment.id(),
                nickname,
                savedComment.content(),
                savedComment.createdAt(),
                true
        );

        return new CreateCommentResponse(commentInfo);
    }

    /**
     * 댓글 수정 도메인 로직
     */
    @Transactional
    public PatchCommentResponse patchComment(Long commentId, @Valid PatchCommentRequest request) {

        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        mapper.update(commentId, request.content());

        CommentInfo patchedComment = mapper.findByIdToCommentInfo(commentId, comment.userId())
                .orElseThrow(() -> new IllegalStateException("댓글 수정 후 데이터를 가져오는 데 실패했습니다."));

        return new PatchCommentResponse(patchedComment);
    }

    /**
     * 프로젝트 댓글 목록 조회 도메인 로직
     */
    public GetCommentsResponse getComments(Long projectId, Long currentUserId) {

        List<CommentInfo> comments = mapper.findAllByProjectId(projectId, currentUserId);

        return new GetCommentsResponse(comments);
    }

    /**
     * 내 댓글 목록 조회 도메인 로직
     */
    public GetMyCommentsResponse getMyComments(Long userId) {

        List<MyCommentInfo> myComments = mapper.findAllByUserId(userId);

        return new GetMyCommentsResponse(myComments);
    }

    /**
     * 내 댓글 삭제 도메인 로직
     */
    @Transactional
    public DeleteMyCommentResponse deleteMyComment(Long commentId, Long userId) {

        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.userId().equals(userId)) {
            throw new IllegalArgumentException("본인의 댓글만 삭제할 수 있습니다.");
        }

        repository.deleteById(commentId);
        return new DeleteMyCommentResponse(commentId);
    }
}