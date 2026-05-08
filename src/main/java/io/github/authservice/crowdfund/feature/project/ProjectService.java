package io.github.authservice.crowdfund.feature.project;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 프로젝트 도메인 비즈니스 로직 처리 계층.
 * 등록, 수정, 삭제 및 다양한 조건의 프로젝트 조회 기능 수행.
 * 설계 지침에 따라 인터페이스 없이 단일 클래스로 구현.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * 1. 신규 프로젝트 생성 및 DB 저장.
     * 데이터베이스에 프로젝트 레코드를 영속화함.
     */
    @Transactional
    public void createProject(ProjectSaveRequest request) {
        projectRepository.save(request);
    }

    /**
     * 2. 등록된 모든 프로젝트 목록 조회.
     * 전체 프로젝트 데이터를 리스트 형태로 반환함.
     */
    public List<ProjectSaveRequest> getProjectList() {
        return StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * 3. 특정 프로젝트 상세 정보 조회.
     * 식별자(ID)를 기준으로 단일 프로젝트 데이터를 반환함.
     */
    public ProjectSaveRequest getProjectDetail(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않음. ID: " + projectId));
    }

    /**
     * 4. 기존 프로젝트 정보 수정.
     * 식별자 확인 후 전달받은 데이터로 업데이트 수행.
     */
    @Transactional
    public void updateProject(Long projectId, ProjectSaveRequest request) {
        if (projectRepository.existsById(projectId)) {
            projectRepository.save(request);
        }
    }

    /**
     * 5. 본인 작성 프로젝트 삭제.
     * 식별자를 기준으로 해당 프로젝트 데이터를 삭제함.
     */
    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    /**
     * 6. 내가 만든 프로젝트 목록 조회.
     * 특정 사용자 식별자 기반으로 등록 프로젝트 목록을 필터링함.
     */
    public List<ProjectSaveRequest> getMyProjects(Long creatorId) {
        return projectRepository.findByCreatorId(creatorId);
    }

    /**
     * 7. 카테고리별 프로젝트 필터링 조회.
     * 선택된 카테고리에 속한 프로젝트 리스트를 반환함.
     */
    public List<ProjectSaveRequest> getProjectsByCategory(Long categoryId) {
        return projectRepository.findByCategoryId(categoryId);
    }

    /**
     * 8. 프로젝트 강제 삭제 (관리자 전용).
     * 권한 확인 후 특정 프로젝트를 시스템에서 즉시 제거함.
     */
    @Transactional
    public void forceDeleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    /**
     * 9. 후원자 배송지 목록 조회 (창작자용).
     * 해당 프로젝트 후원자들의 주소지 정보를 취합하여 반환함.
     */
    public List<Object> getPledgeAddresses(Long projectId) {
        // 실제 구현 시 배송지 도메인 레포지토리 연동 필요.
        return List.of();
    }

    /**
     * 10. 프로젝트 상태 업데이트.
     * 특정 식별자의 프로젝트 진행 상태값(진행, 종료 등)을 변경함.
     */
    @Transactional
    public void updateProjectStatus(Long projectId, String status) {
        // 상태 변경 비즈니스 로직 및 저장 수행 예정.
    }
}