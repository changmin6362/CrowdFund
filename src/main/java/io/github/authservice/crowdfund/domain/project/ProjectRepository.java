package io.github.authservice.crowdfund.domain.project;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 프로젝트 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface ProjectRepository extends ListCrudRepository<Project, Long> {
    /**
     * 생성자 ID로 프로젝트 목록을 조회합니다.
     *
     * @param creatorId 생성자 ID
     * @return 프로젝트 목록
     */
    List<Project> findByCreatorId(Long creatorId);

    /**
     * 카테고리 ID로 프로젝트 목록을 조회합니다.
     *
     * @param categoryId 카테고리 ID
     * @return 프로젝트 목록
     */
    List<Project> findByCategoryId(Integer categoryId);

    /**
     * 프로젝트 상태로 프로젝트 목록을 조회합니다.
     *
     * @param status 프로젝트 상태
     * @return 프로젝트 목록
     */
    List<Project> findByStatus(ProjectStatus status);
}
