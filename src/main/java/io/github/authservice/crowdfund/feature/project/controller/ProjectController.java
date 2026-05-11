package io.github.authservice.crowdfund.feature.project.controller;

import io.github.authservice.crowdfund.feature.project.dto.*;
import io.github.authservice.crowdfund.feature.project.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 프로젝트 도메인 HTTP 요청 수신 및 응답 처리 계층.
 * 모든 요청은 전용 Response DTO를 반환하며 단일 객체 응답 원칙을 준수함.
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 1. 신규 프로젝트 생성 기능을 수행함.
     * @param request 프로젝트 생성 정보
     * @return CreateProjectResponse 생성 결과 (ID, 메시지)
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProjectResponse createProject(@Valid @RequestBody ProjectCreateRequest request) {
        return projectService.createProject(request);
    }

    /**
     * 2. 시스템의 대표 프로젝트 정보를 조회함.
     * @return ProjectResponse 프로젝트 상세 데이터
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public ProjectResponse getProjects() {
        return projectService.getProjects();
    }

    /**
     * 3. 특정 ID를 가진 프로젝트의 상세 데이터를 조회함.
     * @param projectId 프로젝트 식별 번호
     * @return ProjectResponse 특정 프로젝트 상세 정보
     */
    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public ProjectResponse getProjectDetail(@PathVariable Long projectId) {
        return projectService.getProjectDetail(projectId);
    }

    /**
     * 4. 기존 프로젝트의 정보를 수정함.
     * @param projectId 프로젝트 식별 번호
     * @param request   수정할 프로젝트 데이터
     * @return ProjectResponse 수정 완료된 데이터 객체
     */
    @PutMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public ProjectResponse updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectUpdateRequest request) {
        return projectService.updateProject(projectId, request);
    }

    /**
     * 5. 사용자가 생성한 프로젝트를 삭제 처리함.
     * @param projectId 프로젝트 식별 번호
     * @return DeleteProjectResponse 삭제 성공 결과
     */
    @DeleteMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public DeleteProjectResponse deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return new DeleteProjectResponse(projectId, "프로젝트 삭제 성공");
    }

    /**
     * 6. 특정 사용자가 생성한 대표 프로젝트를 조회함.
     * @param userId 사용자(창작자) 식별 번호
     * @return ProjectResponse 해당 사용자의 프로젝트 데이터
     */
    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public ProjectResponse getProjectsByUser(@PathVariable Long userId) {
        return projectService.getProjectsByUser(userId);
    }

    /**
     * 7. 특정 카테고리 내의 대표 프로젝트를 조회함.
     * @param categoryId 카테고리 식별 번호 (Integer로 변경)
     * @return ProjectResponse 해당 카테고리 프로젝트 데이터
     */
    @GetMapping("/category/{categoryId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public ProjectResponse getProjectsByCategory(@PathVariable Integer categoryId) {
        // 팀장님 피드백 반영: Long -> Integer 타입 변경
        return projectService.getProjectsByCategory(categoryId);
    }

    /**
     * 8. 관리자 권한으로 특정 프로젝트를 강제 제거함.
     * @param projectId 프로젝트 식별 번호
     * @return DeleteProjectResponse 강제 삭제 결과
     */
    @DeleteMapping("/admin/{projectId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public DeleteProjectResponse forceDeleteProject(@PathVariable Long projectId) {
        projectService.forceDeleteProject(projectId);
        return new DeleteProjectResponse(projectId, "관리자 프로젝트 강제 삭제 성공");
    }

    /**
     * 9. 해당 프로젝트의 후원자 배송지 정보를 조회함.
     * @param projectId 프로젝트 식별 번호
     * @return PledgeAddressResponse 후원자 배송지 데이터 객체
     */
    @GetMapping("/{projectId}/addresses")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public PledgeAddressResponse getPledgeAddresses(@PathVariable Long projectId) {
        return projectService.getPledgeAddresses(projectId);
    }

    /**
     * 10. 프로젝트의 현재 진행 상태를 갱신함.
     * @param projectId 프로젝트 식별 번호
     * @param request   상태 변경 요청 데이터
     * @return UpdateProjectResponse 변경 성공 결과
     */
    @PatchMapping("/{projectId}/status")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public UpdateProjectResponse updateProjectStatus(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectStatusUpdateRequest request
    ) {
        // 팀장님 피드백 반영: Record 타입이므로 getStatus() -> status() 사용
        projectService.updateProjectStatus(projectId, request.status());
        return new UpdateProjectResponse(projectId, request.status(), "프로젝트 상태 변경 성공");
    }
}