package io.github.crowdfund.domain.pledge;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 후원 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface PledgeRepository extends ListCrudRepository<Pledge, Long> {
    /**
     * 특정 사용자가 특정 프로젝트에 후원했는지 여부를 확인합니다.
     */
    boolean existsByUserIdAndProjectId(Long userId, Long projectId);

    /**
     * 특정 프로젝트에 후원자가 존재하는지 여부를 확인합니다.
     */
    boolean existsByProjectId(Long projectId);
}
