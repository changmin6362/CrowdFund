package io.github.crowdfund.domain.comment;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 댓글 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface CommentRepository extends ListCrudRepository<Comment, Long> {
}
