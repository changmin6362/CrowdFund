package io.github.authservice.crowdfund.domain.pledge;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PledgeRepository extends ListCrudRepository<Pledge, Long> {
    List<Pledge> findByUserId(Long userId);
    List<Pledge> findByProjectId(Long projectId);
}
