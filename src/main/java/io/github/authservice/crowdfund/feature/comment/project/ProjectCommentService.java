package io.github.authservice.crowdfund.feature.comment.project;

import io.github.authservice.crowdfund.domain.comment.Comment;
import io.github.authservice.crowdfund.domain.comment.CommentRepository;
import io.github.authservice.crowdfund.domain.comment.mapper.CommentMapper;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.comment.project.dto.CommentInfo;
import io.github.authservice.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateResponse;
import io.github.authservice.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateRequest;
import io.github.authservice.crowdfund.feature.comment.project.dto.delete.ProjectCommentDeleteResponse;
import io.github.authservice.crowdfund.feature.comment.project.dto.fetch.ProjectCommentsFetchResponse;
import io.github.authservice.crowdfund.feature.comment.project.dto.update.ProjectCommentUpdateResponse;
import io.github.authservice.crowdfund.feature.comment.project.dto.update.ProjectCommentUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectCommentService {
    private final CommentRepository repository;
    private final UserRepository userRepository;
    private final CommentMapper mapper;

    /**
     * 프로젝트에 댓글 작성 도메인 로직
     */
    @Transactional
    public ProjectCommentCreateResponse create(Long projectId, Long userId, @Valid ProjectCommentCreateRequest request) {

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

        return new ProjectCommentCreateResponse(commentInfo);
    }

    /**
     * 프로젝트의 댓글 목록 조회 도메인 로직
     */
    public ProjectCommentsFetchResponse fetch(Long projectId, Long currentUserId) {

        List<CommentInfo> comments = mapper.findAllByProjectId(projectId, currentUserId);

        return new ProjectCommentsFetchResponse(comments);
    }


    /**
     * 프로젝트에 작성한 댓글 수정 도메인 로직
     */
    @Transactional
    public ProjectCommentUpdateResponse update(Long commentId, Long userId, @Valid ProjectCommentUpdateRequest request) {

        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        mapper.update(commentId, request.content());

        if (!comment.userId().equals(userId)) {
            throw new IllegalArgumentException("본인의 댓글만 삭제할 수 있습니다.");
        }

        CommentInfo patchedComment = mapper.findByIdToCommentInfo(commentId, comment.userId())
                .orElseThrow(() -> new IllegalStateException("댓글 수정 후 데이터를 가져오는 데 실패했습니다."));

        return new ProjectCommentUpdateResponse(patchedComment);
    }

    /**
     * 프로젝트에 작성한 댓글 삭제 도메인 로직
     */
    @Transactional
    public ProjectCommentDeleteResponse delete(Long commentId, Long userId) {

        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.userId().equals(userId)) {
            throw new IllegalArgumentException("본인의 댓글만 삭제할 수 있습니다.");
        }

        repository.deleteById(commentId);
        return new ProjectCommentDeleteResponse(commentId);
    }
}