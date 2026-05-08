package io.github.authservice.crowdfund.feature.project;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 프로젝트 도메인 HTTP 요청 수신 및 응답 처리 계층.
 * 모든 요청에 대해 유효성 검증을 수행하며 처리 결과를 객체 형태로 반환함.
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * @param request 프로젝트 생성 정보
     * @return CreateProjectResponse 생성된 프로젝트 결과 정보 (ID, 메시지)
     */
    @PostMapping
    public CreateProjectResponse createProject(@Valid @RequestBody ProjectSaveRequest request) {
        return projectService.createProject(request);
    }

    /**
     * @return 전체 프로젝트 목록 리스트
     */
    @GetMapping
    public List<ProjectSaveRequest> getProjectList() {
        return projectService.getProjectList();
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @return 특정 프로젝트 상세 정보
     */
    @GetMapping("/{projectId}")
    public ProjectSaveRequest getProjectDetail(@PathVariable Long projectId) {
        return projectService.getProjectDetail(projectId);
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @param request   수정할 프로젝트 정보 데이터
     * @return 수정 완료된 프로젝트 데이터
     */
    @PutMapping("/{projectId}")
    public ProjectSaveRequest updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectSaveRequest request) {
        return projectService.updateProject(projectId, request);
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @return 삭제 완료 메시지
     */
    @DeleteMapping("/{projectId}")
    public String deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
        return "프로젝트 삭제 완료";
    }

    /**
     * @param userId 사용자(창작자) 식별 번호
     * @return 해당 사용자의 프로젝트 목록
     */
    @GetMapping("/me")
    public List<ProjectSaveRequest> getMyProjects(@RequestParam Long userId) {
        return projectService.getMyProjects(userId);
    }

    /**
     * @param categoryId 카테고리 식별 번호
     * @return 해당 카테고리 소속 프로젝트 목록
     */
    @GetMapping("/category/{categoryId}")
    public List<ProjectSaveRequest> getProjectsByCategory(@PathVariable Long categoryId) {
        return projectService.getProjectsByCategory(categoryId);
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @return 관리자 강제 삭제 결과 메시지
     */
    @DeleteMapping("/{projectId}/force")
    public String forceDeleteProject(@PathVariable Long projectId) {
        projectService.forceDeleteProject(projectId);
        return "관리자 권한 강제 삭제 성공";
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @return 후원자 배송지 목록 데이터
     */
    @GetMapping("/{projectId}/pledge-addresses")
    public List<Object> getPledgeAddresses(@PathVariable Long projectId) {
        return projectService.getPledgeAddresses(projectId);
    }

    /**
     * @param projectId 프로젝트 식별 번호
     * @param status    변경할 상태값
     * @return 상태 변경 완료 메시지
     */
    @PatchMapping("/{projectId}/status")
    public String updateProjectStatus(@PathVariable Long projectId, @RequestParam String status) {
        projectService.updateProjectStatus(projectId, status);
        return "상태 업데이트 성공: " + status;
    }
}