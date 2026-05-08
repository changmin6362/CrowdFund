package io.github.authservice.crowdfund.feature.project;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 프로젝트 도메인 데이터베이스 접근 인터페이스.
 * Spring Data JDBC의 CrudRepository를 상속받음.
 */
@Repository
public interface ProjectRepository extends CrudRepository<ProjectSaveRequest, Long> {

    /**
     * @param creatorId 창작자 식별 번호
     * @return 특정 사용자의 프로젝트 목록 리스트
     */
    List<ProjectSaveRequest> findByCreatorId(Long creatorId);

    /**
     * @param categoryId 카테고리 식별 번호
     * @return 해당 카테고리의 프로젝트 목록 리스트
     */
    List<ProjectSaveRequest> findByCategoryId(Long categoryId);
}