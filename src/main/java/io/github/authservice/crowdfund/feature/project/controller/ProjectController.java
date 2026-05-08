package io.github.authservice.crowdfund.feature.project.controller;

import io.github.authservice.crowdfund.feature.project.dto.CreateProjectResponse;
import io.github.authservice.crowdfund.feature.project.dto.DeleteProjectResponse;
import io.github.authservice.crowdfund.feature.project.dto.ProjectSaveRequest;
import io.github.authservice.crowdfund.feature.project.dto.UpdateProjectResponse;
import io.github.authservice.crowdfund.feature.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 프로젝트 도메인 HTTP 요청 수신 및 응답 처리 계층.
 * 유효성 검증을 거친 요청을 서비스로 전달하고 전용 DTO 객체로 결과를 반환함.
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 1. 신규 프로젝트 생성 기능을 수행함.
     *
     * @param request 프로젝트 생성 정보
     * @return CreateProjectResponse 생성된 프로젝트 ID와 성공 메시지
     */
    @PostMapping
    public CreateProjectResponse createProject(@Valid @RequestBody ProjectSaveRequest request) {
        return projectService.createProject(request);
    }

    /**
     * 2. 시스템에 등록된 전체 프로젝트 목록을 조회함.
     *
     * @return 전체 프로젝트 목록 리스트
     */
    @GetMapping
    public List<ProjectSaveRequest> getProjectList() {
        return projectService.getProjectList();
    }

    /**
     * 3. 특정 ID를 가진 프로젝트의 상세 데이터를 조회함.
     *
     * @param projectId 프로젝트 식별 번호
     * @return 특정 프로젝트 상세 정보
     */
    @GetMapping("/{projectId}")
    public ProjectSaveRequest getProjectDetail(@PathVariable Long projectId) {
        return projectService.getProjectDetail(projectId);
    }

    /**
     * 4. 기존 프로젝트의 정보를 수정하고 결과를 반환함.
     *
     * @param projectId 프로젝트 식별 번호
     * @param request   수정할 프로젝트 정보 데이터
     * @return ProjectSaveRequest 수정 완료된 데이터 객체
     */
    @PutMapping("/{projectId}")
    public ProjectSaveRequest updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectSaveRequest request) {
        return projectService.updateProject(projectId, request);
    }

    /**
     * 5. 사용자가 생성한 프로젝트를 삭제 처리함.
     *
     * @param projectId 프로젝트 식별 번호
     * @return DeleteProjectResponse 삭제된 ID와 성공 메시지
     */
    @DeleteMapping("/{projectId}")
    public DeleteProjectResponse deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new DeleteProjectResponse(projectId, "프로젝트 삭제 성공");
    }

    /**
     * 6. 현재 사용자가 생성한 프로젝트 목록만 필터링하여 조회함.
     *
     * @param userId 사용자(창작자) 식별 번호
     * @return 해당 사용자의 프로젝트 목록 리스트
     */
    @GetMapping("/me")
    public List<ProjectSaveRequest> getMyProjects(@RequestParam Long userId) {
        return projectService.getMyProjects(userId);
    }

    /**
     * 7. 카테고리별로 분류된 프로젝트 목록을 조회함.
     *
     * @param categoryId 카테고리 식별 번호
     * @return 해당 카테고리 소속 프로젝트 목록 리스트
     */
    @GetMapping("/category/{categoryId}")
    public List<ProjectSaveRequest> getProjectsByCategory(@PathVariable Long categoryId) {
        return projectService.getProjectsByCategory(categoryId);
    }

    /**
     * 8. 관리자 권한으로 특정 프로젝트를 시스템에서 제거함.
     *
     * @param projectId 프로젝트 식별 번호
     * @return DeleteProjectResponse 강제 삭제 결과 데이터
     */
    @DeleteMapping("/{projectId}/force")
    public DeleteProjectResponse forceDeleteProject(@PathVariable Long projectId) {
        projectService.forceDeleteProject(projectId);
        return new DeleteProjectResponse(projectId, "관리자 권한 강제 삭제 완료");
    }

    /**
     * 9. 해당 프로젝트에 참여한 후원자들의 배송지 주소를 조회함.
     *
     * @param projectId 프로젝트 식별 번호
     * @return 후원자 배송지 정보 목록
     */
    @GetMapping("/{projectId}/pledge-addresses")
    public List<Object> getPledgeAddresses(@PathVariable Long projectId) {
        return projectService.getPledgeAddresses(projectId);
    }

    /**
     * 10. 프로젝트의 현재 진행 상태(펀딩 중, 종료 등)를 갱신함.
     *
     * @param projectId 프로젝트 식별 번호
     * @param status    변경할 상태값 (String)
     * @return UpdateProjectResponse 변경된 상태 정보와 결과 메시지
     */
    @PatchMapping("/{projectId}/status")
    public UpdateProjectResponse updateProjectStatus(@PathVariable Long projectId, @RequestParam String status) {
        projectService.updateProjectStatus(projectId, status);
        return new UpdateProjectResponse(projectId, status, "상태 업데이트 성공");
    }
}