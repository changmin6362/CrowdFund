package io.github.crowdfund.feature.comment.my;

import io.github.crowdfund.domain.comment.Comment;
import io.github.crowdfund.domain.comment.CommentRepository;
import io.github.crowdfund.domain.comment.mapper.CommentMapper;
import io.github.crowdfund.feature.comment.my.dto.delete.ProjectCommentDeleteResponse;
import io.github.crowdfund.feature.comment.my.dto.fetch.MyCommentInfo;
import io.github.crowdfund.feature.comment.my.dto.fetch.MyCommentsResponse;
import io.github.crowdfund.feature.comment.my.dto.update.ProjectCommentUpdateRequest;
import io.github.crowdfund.feature.comment.my.dto.update.ProjectCommentUpdateResponse;
import io.github.crowdfund.feature.comment.project.dto.CommentInfo;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.github.crowdfund.global.common.dto.pagination.CursorResponse;
import io.github.crowdfund.global.common.pagination.CursorPaginationProcessor;
import io.github.crowdfund.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyCommentService {
    private final CommentRepository repository;
    private final CommentMapper mapper;

    /**
     * 내 댓글 목록 조회 도메인 로직
     */
    public MyCommentsResponse fetch(Long userId, CursorRequest cursorRequest, Integer limit) {
        // 1. 객체 내부 로직을 활용해 입력값 검증
        cursorRequest.validate();

        // 2. 데이터 목록 조회 (다음 페이지 존재 여부 확인을 위해 limit보다 1개를 더 조회)
        List<MyCommentInfo> myComments = mapper.findAllByUserId(
                userId,
                cursorRequest.createdAt(),
                cursorRequest.id(),
                limit + 1
        );

        // 3. 다음 요청에 사용할 복합 커서를 처리함
        CursorResponse<MyCommentInfo, CursorRequest> response = CursorPaginationProcessor.convertToCursorResponse(
                myComments,
                limit,
                item -> new CursorRequest(item.createdAt(), item.commentId())
        );

        return new MyCommentsResponse(response.content(), response.hasNext(), response.nextCursor());
    }

    /**
     * 내 댓글 수정 도메인 로직
     */
    @Transactional
    public ProjectCommentUpdateResponse update(Long commentId, SecurityUser securityUser, @Valid ProjectCommentUpdateRequest request) {

        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (securityUser.isOwner(comment.userId())) {
            throw new IllegalArgumentException("본인의 댓글만 수정할 수 있습니다.");
        }

        mapper.update(commentId, request.content());

        CommentInfo patchedComment = mapper.findByIdToCommentInfo(commentId, comment.userId())
                .orElseThrow(() -> new IllegalStateException("댓글 수정 후 데이터를 가져오는 데 실패했습니다."));

        return new ProjectCommentUpdateResponse(patchedComment);
    }

    /**
     * 내 댓글 삭제 도메인 로직
     */
    @Transactional
    public ProjectCommentDeleteResponse delete(Long commentId, SecurityUser securityUser) {

        Comment comment = repository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (securityUser.isOwner(comment.userId())) {
            throw new IllegalArgumentException("본인의 댓글만 삭제할 수 있습니다.");
        }

        repository.deleteById(commentId);
        return new ProjectCommentDeleteResponse(commentId);
    }
}