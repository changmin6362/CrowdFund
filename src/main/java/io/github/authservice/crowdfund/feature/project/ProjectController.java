package io.github.authservice.crowdfund.feature.project;

import io.github.authservice.crowdfund.feature.project.request.ProjectCreateRequest;
import io.github.authservice.crowdfund.feature.project.request.ProjectStatusUpdateRequest;
import io.github.authservice.crowdfund.feature.project.request.ProjectUpdateRequest;
import io.github.authservice.crowdfund.feature.project.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * 프로젝트 도메인 HTTP 요청 수신 및 응답 처리 계층.
 * 모든 요청은 전용 Response DTO를 반환하며 단일 객체 응답 원칙을 준수함.
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 1. 프로젝트 생성
     *
     * @param request 프로젝트 생성 정보
     * @return CreateProjectResponse 생성 결과 (ID, 메시지)
     */
    @PostMapping("/projects")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProjectResponse createProject(@Valid @RequestBody ProjectCreateRequest request) {
        return projectService.createProject(request);
    }

    /**
     * 2. 프로젝트 목록조회
     *
     * @return GetProjectResponse 프로젝트 상세 데이터
     */
    @GetMapping("/projects")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public GetProjectResponse getProjects() {
        return projectService.getProjects();
    }

    /**
     * 3. 프로젝트 상세조회
     *
     * @param projectId 프로젝트 식별 번호
     * @return GetProjectDetailResponse 특정 프로젝트 상세 정보
     */
    @GetMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public GetProjectDetailResponse getProjectDetail(@PathVariable Long projectId) {
        return projectService.getProjectDetail(projectId);
    }

    /**
     * 4. 프로젝트 수정
     *
     * @param projectId 프로젝트 식별 번호
     * @param request   수정할 프로젝트 데이터
     * @return ProjectResponse 수정 완료된 데이터 객체
     */
    @PutMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public UpdateProjectResponse updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectUpdateRequest request) {
        return projectService.updateProject(projectId, request);
    }

    /**
     * 5. 프로젝트 삭제
     *
     * @param projectId 프로젝트 식별 번호
     * @return DeleteProjectResponse 삭제 성공 결과
     */
    @DeleteMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public DeleteProjectResponse deleteProject(@PathVariable Long projectId) {
        return projectService.deleteProject(projectId);
    }

    /**
     * 6. 내 프로젝트 조회
     *
     * @param userId 사용자(창작자) 식별 번호
     * @return ProjectResponse 해당 사용자의 프로젝트 데이터
     */
    @GetMapping("/users/me/projects/{userId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public GetProjectsByUserResponse getProjectsByUser(@PathVariable Long userId) {
        return projectService.getProjectsByUser(userId);
    }

    /**
     * 7. 해당 프로젝트의 후원자 배송지 정보를 조회함.
     *
     * @param projectId 프로젝트 식별 번호
     * @return PledgeAddressResponse 후원자 배송지 데이터 객체
     */
    @GetMapping("/projects/{projectId}/addresses")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public GetPledgeAddressResponse getPledgeAddresses(@PathVariable Long projectId) {
        return projectService.getPledgeAddresses(projectId);
    }


    /**
     * 8. 카테고리별 프로젝트 조회.
     *
     * @param categoryId 카테고리 식별 번호 (Integer로 변경)
     * @return ProjectResponse 해당 카테고리 프로젝트 데이터
     */
    @GetMapping("/projects?categoryId=1")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public GetProjectsByCategory getProjectsByCategory(@RequestParam Integer categoryId) {
        // 팀장님 피드백 반영: Long -> Integer 타입 변경
        return projectService.getProjectsByCategory(categoryId);
    }

    /**
     * 9. 관리자 권한으로 특정 프로젝트를 강제 제거함.
     *
     * @param projectId 프로젝트 식별 번호
     * @return DeleteProjectResponse 강제 삭제 결과
     */
    @DeleteMapping("/admin/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public ForceDeleteProjectResponse forceDeleteProject(@PathVariable Long projectId) {
        return projectService.forceDeleteProject(projectId);
    }


    /**
     * 10. 프로젝트의 현재 진행 상태를 갱신함.
     *
     * @param projectId 프로젝트 식별 번호
     * @param request   상태 변경 요청 데이터
     * @return UpdateProjectResponse 변경 성공 결과
     */
    @PatchMapping("/projects/{projectId}/status")
    @ResponseStatus(HttpStatus.OK) // 팀장님 피드백 반영: OK 추가
    public UpdateProjectStatusResponse updateProjectStatus(
            @PathVariable Long projectId,
            @Valid @RequestBody ProjectStatusUpdateRequest request
    ) {
        // 팀장님 피드백 반영: Record 타입이므로 getStatus() -> status() 사용
        return projectService.updateProjectStatus(projectId, request.status());
    }
}