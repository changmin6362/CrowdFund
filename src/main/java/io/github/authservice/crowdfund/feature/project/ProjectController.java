package io.github.authservice.crowdfund.feature.project;

import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.feature.project.request.CreateProjectRequest;
import io.github.authservice.crowdfund.feature.project.request.PatchProjectStatusRequest;
import io.github.authservice.crowdfund.feature.project.request.UpdateProjectRequest;
import io.github.authservice.crowdfund.feature.project.response.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Project", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/api")
@Validated
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService service;

    /**
     * 프로젝트 생성
     *
     * @param request 프로젝트 생성 정보
     * @return message, projectId
     */
    @PostMapping("/projects/{creatorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateProjectResponse createProject(@PathVariable Long creatorId, @Valid @RequestBody CreateProjectRequest request) {
        return service.createProject(creatorId, request);
    }

    /**
     * 프로젝트 목록 조회
     *
     * @param statuses   프로젝트 상태 필터링
     * @param categoryId 카테고리 ID 필터링
     * @return message, projectList
     */
    @GetMapping("/projects")
    @ResponseStatus(HttpStatus.OK)
    public GetProjectResponse getProjects(
            @RequestParam(required = false) @NotEmpty(message = "statuses는 비어있을 수 없습니다.") List<ProjectStatus> statuses,
            @RequestParam(required = false) @Positive(message = "카테고리 ID는 양수여야 합니다.") Integer categoryId
    ) {
        return service.getProjects(statuses, categoryId);
    }

    /**
     * 프로젝트 상세 조회
     *
     * @param projectId 프로젝트 ID
     * @return message, projectDetail
     */
    @GetMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public GetProjectDetailResponse getProjectDetail(@PathVariable Long projectId) {
        return service.getProjectDetail(projectId);
    }

    /**
     * 프로젝트 제목과 본문 수정
     *
     * @param projectId 프로젝트 ID
     * @param request   수정할 프로젝트 정보
     * @return message
     */
    @PatchMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public UpdateProjectResponse updateProject(@PathVariable Long projectId, @Valid @RequestBody UpdateProjectRequest request) {
        return service.updateProject(projectId, request);
    }

    /**
     * 프로젝트 삭제
     *
     * @param projectId 프로젝트 ID
     * @return message
     */
    @DeleteMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteProjectResponse deleteProject(@PathVariable Long projectId) {
        return service.deleteProject(projectId);
    }

    /**
     * 내 프로젝트 조회
     *
     * @param userId 사용자 ID
     * @return message, projectList
     */
    @GetMapping("/users/me/projects/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public GetMyProjectsResponse getMyProjects(@PathVariable Long userId) {
        return service.getMyProjects(userId);
    }

    /**
     * 후원자들의 배송 정보 목록 조회
     *
     * @param projectId 프로젝트 ID
     * @return message, shippingInfoList
     */
    @GetMapping("/projects/{projectId}/shipping-infos")
    @ResponseStatus(HttpStatus.OK)
    public GetShippingInfosResponse getShippingInfos(@PathVariable Long projectId) {
        return service.getShippingInfos(projectId);
    }

    /**
     * 프로젝트의 상태 갱신
     *
     * @param projectId 프로젝트 식별 번호
     * @param request   상태 변경 요청 데이터
     * @return UpdateProjectResponse 변경 성공 결과
     */
    @PatchMapping("/projects/{projectId}/status")
    @ResponseStatus(HttpStatus.OK)
    public PatchProjectStatusResponse patchProjectStatus(
            @PathVariable Long projectId,
            @Valid @RequestBody PatchProjectStatusRequest request
    ) {
        return service.patchProjectStatus(projectId, request);
    }
}