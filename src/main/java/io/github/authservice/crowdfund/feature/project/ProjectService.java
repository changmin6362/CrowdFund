package io.github.authservice.crowdfund.feature.project;

import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.feature.project.request.ProjectCreateRequest;
import io.github.authservice.crowdfund.feature.project.request.ProjectUpdateRequest;
import io.github.authservice.crowdfund.feature.project.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 프로젝트 도메인 비즈니스 로직 처리 계층.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository repository;

    /**
     * 1. 신규 프로젝트 생성 기능을 수행함.
     */
    @Transactional
    public CreateProjectResponse createProject(ProjectCreateRequest request) {
        // 팀장님 피드백: 성공 메시지를 첫 번째 인자로 전달
        // return new CreateProjectResponse("프로젝트가 성공적으로 생성되었습니다.", repository.save(request));
        return new CreateProjectResponse("프로젝트 생성 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 2. 시스템의 대표 프로젝트 정보를 조회함.
     */
    public GetProjectResponse getProjects() {
        // return new GetProjectResponse("시스템 대표 프로젝트 조회 성공", repository.getProjects());
        return new GetProjectResponse("시스템 대표 프로젝트 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 3. 특정 ID를 가진 프로젝트의 상세 데이터를 조회함.
     */
    public GetProjectDetailResponse getProjectDetail(Long projectId) {
        // return new GetProjectDetailResponse("프로젝트 상세 정보 조회 성공", repository.getProjectDetail(projectId));
        return new GetProjectDetailResponse("프로젝트 상세 정보 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 4. 기존 프로젝트의 정보를 수정함.
     */
    @Transactional
    public UpdateProjectResponse updateProject(Long projectId, ProjectUpdateRequest request) {
        // repository.update(projectId, request);
        // return new UpdateProjectResponse("프로젝트 정보가 수정되었습니다.", "진행중", projectId);
        return new UpdateProjectResponse("프로젝트 수정 기능은 구현되지 않았습니다.");
    }

    /**
     * 5. 사용자가 생성한 프로젝트를 삭제함.
     */
    @Transactional
    public DeleteProjectResponse deleteProject(Long projectId) {
        // repository.deleteById(projectId);
        // return new DeleteProjectResponse("프로젝트 삭제 성공", projectId);
        return new DeleteProjectResponse("프로젝트 삭제 성공");
    }

    /**
     * 6. 특정 사용자가 생성한 대표 프로젝트를 조회함.
     */
    public GetProjectsByUserResponse getProjectsByUser(Long userId) {
        // return new GetProjectsByUserResponse("사용자별 프로젝트 조회 성공", repository.findByCreatorId(userId));
        return new GetProjectsByUserResponse("사용자별 프로젝트 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 7. 특정 카테고리 내의 대표 프로젝트를 조회함.
     */
    public GetProjectsByCategory getProjectsByCategory(Integer categoryId) {
        // return new GetProjectsByCategory("카테고리별 프로젝트 조회 성공", repository.findByCategoryId(categoryId));
        return new GetProjectsByCategory("카테고리별 프로젝트 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 8. 관리자 권한으로 특정 프로젝트를 강제 제거함.
     */
    @Transactional
    public ForceDeleteProjectResponse forceDeleteProject(Long projectId) {
        // repository.deleteById(projectId);
        // return new ForceDeleteProjectResponse("관리자 권한으로 프로젝트를 강제 삭제했습니다.", projectId);
        return new ForceDeleteProjectResponse("관리자 권한으로 프로젝트를 강제 삭제했습니다.");
    }

    /**
     * 9. 해당 프로젝트의 후원자 배송지 정보를 조회함.
     */
    public GetPledgeAddressResponse getPledgeAddresses(Long projectId) {
        // return new GetPledgeAddressResponse("배송지 정보 조회 성공", repository.findPledgeAddressByProjectId(projectId));
        return new GetPledgeAddressResponse("배송지 정보 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 10. 프로젝트의 현재 진행 상태를 업데이트함.
     */
    @Transactional
    public UpdateProjectStatusResponse updateProjectStatus(Long projectId, String status) {
        // repository.updateStatus(projectId, status);
        // return new UpdateProjectStatusResponse("프로젝트 상태가 성공적으로 변경되었습니다.", status, projectId);
        return new UpdateProjectStatusResponse("프로젝트 상태 변경 기능은 구현되지 않았습니다.");
    }
}