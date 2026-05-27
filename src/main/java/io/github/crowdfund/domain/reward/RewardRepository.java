package io.github.crowdfund.domain.reward;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 보상 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface RewardRepository extends ListCrudRepository<Reward, Long> {
    /**
     * 특정 프로젝트에 속한 보상 목록을 조회합니다.
     *
     * @param projectId 프로젝트 ID
     * @return 보상 목록
     */
    List<Reward> findByProjectId(Long projectId);
}
