package io.github.authservice.crowdfund.feature.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 프로젝트 도메인 데이터베이스 접근 인터페이스.
 * Spring Data JDBC의 CrudRepository를 상속받아 기본 CRUD 기능 수행.
 * 설계 지침 준수를 위해 인터페이스 형태로 정의.
 */
@Repository
public interface ProjectRepository extends CrudRepository<ProjectSaveRequest, Long> {

    /**
     * 창작자 식별자 기반 프로젝트 목록 필터링 조회
     */
    List<ProjectSaveRequest> findByCreatorId(Long creatorId);

    /**
     * 카테고리 식별자 기반 프로젝트 목록 필터링 조회
     */
    List<ProjectSaveRequest> findByCategoryId(Long categoryId);
}