package io.github.authservice.crowdfund.domain.comment;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 댓글 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface CommentRepository extends ListCrudRepository<Comment, Long> {
    /**
     * 특정 프로젝트의 모든 댓글을 조회합니다.
     *
     * @param projectId 프로젝트 ID
     * @return 댓글 목록
     */
    List<Comment> findByProjectId(Long projectId);

    /**
     * 특정 사용자가 작성한 모든 댓글을 조회합니다.
     *
     * @param userId 회원 ID
     * @return 댓글 목록
     */
    List<Comment> findByUserId(Long userId);
}
