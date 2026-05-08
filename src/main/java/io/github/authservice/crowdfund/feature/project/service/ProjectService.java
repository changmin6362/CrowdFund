package io.github.authservice.crowdfund.feature.project.service;

import io.github.authservice.crowdfund.feature.project.dto.CreateProjectResponse;
import io.github.authservice.crowdfund.feature.project.repository.ProjectRepository;
import io.github.authservice.crowdfund.feature.project.dto.ProjectSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 프로젝트 도메인 비즈니스 로직 처리 계층.
 * 모든 메서드는 처리 결과를 반환하여 컨트롤러가 응답을 구성할 수 있도록 함.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * @param request 프로젝트 생성 정보
     * @return CreateProjectResponse 생성 결과 객체 (ID, 메시지)
     */
    @Transactional
    public CreateProjectResponse createProject(ProjectSaveRequest request) {
        ProjectSaveRequest saved = projectRepository.save(request);
        return new CreateProjectResponse(saved.id(), "프로젝트가 성공적으로 생성되었습니다.");
    }

    /**
     * @return 전체 프로젝트 목록
     */
    public List<ProjectSaveRequest> getProjectList() {
        return StreamSupport.stream(projectRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @return 조회된 프로젝트 상세 정보
     */
    public ProjectSaveRequest getProjectDetail(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않음. ID: " + projectId));
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @param request   수정할 프로젝트 정보
     * @return 수정 완료된 데이터
     */
    @Transactional
    public ProjectSaveRequest updateProject(Long projectId, ProjectSaveRequest request) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("수정할 프로젝트가 존재하지 않음.");
        }
        return projectRepository.save(request);
    }

    /**
     * @param projectId 프로젝트 식별 번호
     */
    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    /**
     * @param creatorId 창작자 식별 번호
     * @return 특정 사용자의 프로젝트 목록
     */
    public List<ProjectSaveRequest> getMyProjects(Long creatorId) {
        return projectRepository.findByCreatorId(creatorId);
    }

    /**
     * @param categoryId 카테고리 식별 번호
     * @return 해당 카테고리의 프로젝트 목록
     */
    public List<ProjectSaveRequest> getProjectsByCategory(Long categoryId) {
        return projectRepository.findByCategoryId(categoryId);
    }

    /**
     * @param projectId 프로젝트 식별 번호
     */
    @Transactional
    public void forceDeleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @return 후원자 배송 정보 목록
     */
    public List<Object> getPledgeAddresses(Long projectId) {
        return List.of();
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @param status    변경할 상태값
     */
    @Transactional
    public void updateProjectStatus(Long projectId, String status) {
        // 상태 업데이트 로직 구현
    }
}