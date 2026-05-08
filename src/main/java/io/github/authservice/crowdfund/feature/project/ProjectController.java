package io.github.authservice.crowdfund.feature.project;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 프로젝트 도메인 HTTP 요청 수신 및 응답 처리 계층.
 * 프로젝트 생성, 수정, 삭제 및 다중 조건 조회 API 제공.
 */
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    /**
     * 1. 신규 프로젝트 생성
     */
    @PostMapping
    public void createProject(@RequestBody ProjectSaveRequest request) {
        projectService.createProject(request);
    }

    /**
     * 2. 전체 프로젝트 목록 조회
     */
    @GetMapping
    public List<ProjectSaveRequest> getProjectList() {
        return projectService.getProjectList();
    }

    /**
     * 3. 특정 프로젝트 상세 조회
     */
    @GetMapping("/{projectId}")
    public ProjectSaveRequest getProjectDetail(@PathVariable Long projectId) {
        return projectService.getProjectDetail(projectId);
    }

    /**
     * 4. 프로젝트 내용 수정
     */
    @PutMapping("/{projectId}")
    public void updateProject(@PathVariable Long projectId, @RequestBody ProjectSaveRequest request) {
        projectService.updateProject(projectId, request);
    }

    /**
     * 5. 본인 프로젝트 삭제
     */
    @DeleteMapping("/{projectId}")
    public void deleteProject(@PathVariable Long projectId) {
        projectService.deleteProject(projectId);
    }

    /**
     * 6. 내가 만든 프로젝트 조회 (URL: /api/projects/me)
     */
    @GetMapping("/me")
    public List<ProjectSaveRequest> getMyProjects(@RequestParam Long userId) {
        return projectService.getMyProjects(userId);
    }

    /**
     * 7. 카테고리별 프로젝트 조회
     */
    @GetMapping("/category/{categoryId}")
    public List<ProjectSaveRequest> getProjectsByCategory(@PathVariable Long categoryId) {
        return projectService.getProjectsByCategory(categoryId);
    }

    /**
     * 8. 프로젝트 강제 삭제 (관리자용)
     */
    @DeleteMapping("/{projectId}/force")
    public void forceDeleteProject(@PathVariable Long projectId) {
        projectService.forceDeleteProject(projectId);
    }

    /**
     * 9. 후원자 배송지 목록 조회
     */
    @GetMapping("/{projectId}/pledge-addresses")
    public List<Object> getPledgeAddresses(@PathVariable Long projectId) {
        return projectService.getPledgeAddresses(projectId);
    }

    /**
     * 10. 프로젝트 상태 업데이트
     */
    @PatchMapping("/{projectId}/status")
    public void updateProjectStatus(@PathVariable Long projectId, @RequestParam String status) {
        projectService.updateProjectStatus(projectId, status);
    }
}