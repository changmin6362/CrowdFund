package io.github.crowdfund.domain.pledge;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 후원 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface PledgeRepository extends ListCrudRepository<Pledge, Long> {
    /**
     * 특정 사용자의 모든 후원 내역을 조회합니다.
     *
     * @param userId 회원 ID
     * @return 후원 목록
     */
    List<Pledge> findByUserId(Long userId);

    /**
     * 특정 프로젝트의 모든 후원 내역을 조회합니다.
     *
     * @param projectId 프로젝트 ID
     * @return 후원 목록
     */
    List<Pledge> findByProjectId(Long projectId);
}
