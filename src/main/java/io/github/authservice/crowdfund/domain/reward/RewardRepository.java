package io.github.authservice.crowdfund.domain.reward;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends ListCrudRepository<Reward, Long> {
    List<Reward> findByProjectId(Long projectId);
}
