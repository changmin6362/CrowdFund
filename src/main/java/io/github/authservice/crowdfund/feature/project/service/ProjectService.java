package io.github.authservice.crowdfund.feature.project.service;

import io.github.authservice.crowdfund.feature.project.dto.*;
import io.github.authservice.crowdfund.feature.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 프로젝트 도메인 비즈니스 로직 처리 계층.
 * 컨트롤러의 요구 사양에 맞춰 단일 Response 객체를 반환하며 DTO 변환 로직을 포함함.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;

    /**
     * 1. 신규 프로젝트 생성 기능을 수행함.
     * @param request 프로젝트 생성 정보
     * @return CreateProjectResponse 생성 결과 객체 (ID, 메시지)
     */
    @Transactional
    public CreateProjectResponse createProject(ProjectSaveRequest request) {
        ProjectSaveRequest saved = projectRepository.save(request);
        return new CreateProjectResponse(saved.id(), "프로젝트가 성공적으로 생성되었습니다.");
    }

    /**
     * 2. 시스템의 대표 프로젝트 정보를 조회함.
     * @return ProjectResponse 프로젝트 상세 데이터 응답 객체
     */
    public ProjectResponse getProjects() {
        ProjectSaveRequest project = projectRepository.findAll().iterator().next();
        return convertToResponse(project);
    }

    /**
     * 3. 특정 ID를 가진 프로젝트의 상세 데이터를 조회함.
     * @param projectId 프로젝트 식별 번호
     * @return 조회된 프로젝트 상세 응답 객체
     */
    public ProjectResponse getProjectDetail(Long projectId) {
        ProjectSaveRequest project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("해당 프로젝트가 존재하지 않음. ID: " + projectId));
        return convertToResponse(project);
    }

    /**
     * 4. 기존 프로젝트의 정보를 수정함.
     * @param projectId 프로젝트 식별 번호
     * @param request   수정할 프로젝트 정보
     * @return 수정 완료된 상세 응답 객체
     */
    @Transactional
    public ProjectResponse updateProject(Long projectId, ProjectSaveRequest request) {
        if (!projectRepository.existsById(projectId)) {
            throw new IllegalArgumentException("수정할 프로젝트가 존재하지 않음.");
        }
        ProjectSaveRequest updated = projectRepository.save(request);
        return convertToResponse(updated);
    }

    /**
     * 5. 사용자가 생성한 프로젝트를 삭제함. (void)
     * @param projectId 프로젝트 식별 번호
     */
    @Transactional
    public void deleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    /**
     * 6. 특정 사용자가 생성한 대표 프로젝트를 조회함.
     * @param userId 사용자(창작자) 식별 번호
     * @return 해당 사용자의 프로젝트 응답 객체
     */
    public ProjectResponse getProjectsByUser(Long userId) {
        ProjectSaveRequest project = projectRepository.findByCreatorId(userId).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 등록한 프로젝트가 없습니다."));
        return convertToResponse(project);
    }

    /**
     * 7. 특정 카테고리 내의 대표 프로젝트를 조회함.
     * @param categoryId 카테고리 식별 번호
     * @return 해당 카테고리 소속 프로젝트 응답 객체
     */
    public ProjectResponse getProjectsByCategory(Long categoryId) {
        ProjectSaveRequest project = projectRepository.findByCategoryId(categoryId).stream()
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 카테고리에 프로젝트가 없습니다."));
        return convertToResponse(project);
    }

    /**
     * 8. 관리자 권한으로 특정 프로젝트를 강제 제거함. (void)
     * @param projectId 프로젝트 식별 번호
     */
    @Transactional
    public void forceDeleteProject(Long projectId) {
        projectRepository.deleteById(projectId);
    }

    /**
     * 9. 해당 프로젝트의 후원자 배송지 대표 정보를 조회함.
     * @param projectId 프로젝트 식별 번호
     * @return PledgeAddressResponse 배송지 정보 응답 객체
     */
    public PledgeAddressResponse getPledgeAddresses(Long projectId) {
        // 실제 DB 연동 전 임시 객체 반환 (추후 로직 구현)
        return new PledgeAddressResponse(1L, "홍길동", "12345", "대전광역시 유성구", "상세주소");
    }

    /**
     * 10. 프로젝트의 현재 진행 상태를 업데이트함. (void)
     * @param projectId 프로젝트 식별 번호
     * @param status    변경할 상태값
     */
    @Transactional
    public void updateProjectStatus(Long projectId, String status) {
        // 상태 업데이트 로직 (필요 시 세부 필드 수정 로직 추가 가능)
    }

    /**
     * 내부 데이터 모델(Request/Entity)을 응답 전용 모델(Response)로 변환함.
     */
    private ProjectResponse convertToResponse(ProjectSaveRequest entity) {
        return new ProjectResponse(
                entity.id(),
                entity.title(),
                entity.description(),
                entity.goalAmount(),
                0L, // currentAmount 기본값
                entity.startAt(),
                entity.endAt(),
                "진행중", // status 기본값
                entity.categoryId(),
                entity.creatorId()
        );
    }
}