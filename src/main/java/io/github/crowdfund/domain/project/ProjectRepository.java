package io.github.crowdfund.domain.project;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 프로젝트 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {
}
