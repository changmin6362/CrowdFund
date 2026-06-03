package io.github.crowdfund.domain.project;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 프로젝트 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {

    default void validateProjectOwner(Long projectId, Long userId) {
        Project project = findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));

        project.validateOwner(userId);
    }
}
