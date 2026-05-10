package io.github.authservice.crowdfund.domain.project;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {
    List<Project> findByCreatorId(Long creatorId);
    List<Project> findByCategoryId(Long categoryId);
    List<Project> findByStatus(String status);
}
