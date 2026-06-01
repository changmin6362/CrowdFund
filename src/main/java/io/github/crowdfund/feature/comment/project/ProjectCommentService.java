package io.github.crowdfund.feature.comment.project;

import io.github.crowdfund.domain.comment.Comment;
import io.github.crowdfund.domain.comment.CommentRepository;
import io.github.crowdfund.domain.comment.mapper.CommentMapper;
import io.github.crowdfund.domain.user.User;
import io.github.crowdfund.domain.user.UserRepository;
import io.github.crowdfund.feature.comment.project.dto.CommentInfo;
import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateRequest;
import io.github.crowdfund.feature.comment.project.dto.create.ProjectCommentCreateResponse;
import io.github.crowdfund.feature.comment.project.dto.delete.ProjectCommentDeleteResponse;
import io.github.crowdfund.feature.comment.project.dto.fetch.ProjectCommentsFetchResponse;
import io.github.crowdfund.feature.comment.project.dto.update.ProjectCommentUpdateRequest;
import io.github.crowdfund.feature.comment.project.dto.update.ProjectCommentUpdateResponse;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.github.crowdfund.global.common.dto.pagination.CursorResponse;
import io.github.crowdfund.global.common.pagination.CursorPaginationProcessor;
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
    public ProjectCommentsFetchResponse fetch(Long projectId, Long currentUserId, CursorRequest cursorRequest, Integer limit) {
        // 1. 객체 내부 로직을 활용해 입력값 검증
        cursorRequest.validate();

        // 2. 데이터 목록 조회 (다음 페이지 존재 여부 확인을 위해 limit보다 1개를 더 조회)
        List<CommentInfo> comments = mapper.findAllByProjectId(
                projectId,
                currentUserId,
                cursorRequest.createdAt(),
                cursorRequest.id(),
                limit + 1
        );

        // 3. 다음 요청에 사용할 복합 커서를 처리함
        CursorResponse<CommentInfo, CursorRequest> response = CursorPaginationProcessor.convertToCursorResponse(
                comments,
                limit,
                item -> new CursorRequest(item.createdAt(), item.commentId())
        );

        return new ProjectCommentsFetchResponse(response.content(), response.hasNext(), response.nextCursor());
    }


    /**
     * 프로젝트에 작성한 댓글 수정 도메인 로직
     */
    @Transactional
    public ProjectCommentUpdateResponse update(Long commentId, Long userId, @Valid ProjectCommentUpdateRequest request) {

        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.userId().equals(userId)) {
            throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
        }

        mapper.update(commentId, request.content());

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