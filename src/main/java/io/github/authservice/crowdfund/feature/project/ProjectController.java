package io.github.authservice.crowdfund.feature.project;

import io.github.authservice.crowdfund.domain.project.ProjectStatus;
import io.github.authservice.crowdfund.feature.project.request.CreateProjectRequest;
import io.github.authservice.crowdfund.feature.project.request.PatchProjectRequest;
import io.github.authservice.crowdfund.feature.project.request.PatchProjectStatusRequest;
import io.github.authservice.crowdfund.feature.project.response.*;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    public ApiResult<CreateProjectResponse> createProject(@PathVariable Long creatorId, @Valid @RequestBody CreateProjectRequest request) {
        return ApiResult.success("프로젝트 생성에 성공했습니다.", service.createProject(creatorId, request));
    }

    /**
     * 프로젝트 목록 조회 (복합 커서 기반 페이지네이션, 최신순 정렬)
     *
     * @param statuses   프로젝트 상태 필터링
     * @param categoryId 카테고리 ID 필터링
     * @return message, projectList, hasNext, nextCursor
     */
    @GetMapping("/projects")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetProjectResponse> getProjects(
            @RequestParam(required = false) List<ProjectStatus> statuses,
            @RequestParam(required = false) @Positive(message = "카테고리 ID는 양수여야 합니다.") Integer categoryId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "10") @Positive Integer limit
    ) {
        return ApiResult.success("프로젝트 목록 조회에 성공했습니다.", service.getProjects(statuses, categoryId, cursorCreatedAt, cursorId, limit));
    }

    /**
     * 프로젝트 상세 조회
     *
     * @param projectId 프로젝트 ID
     * @return message, projectDetail
     */
    @GetMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetProjectDetailResponse> getProjectDetail(@PathVariable Long projectId) {
        return ApiResult.success("프로젝트 상세 조회에 성공했습니다.", service.getProjectDetail(projectId));
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
    public ApiResult<Void> patchProject(@PathVariable Long projectId, @Valid @RequestBody PatchProjectRequest request) {
        service.patchProject(projectId, request);

        return ApiResult.success("프로젝트 수정에 성공했습니다.");
    }

    /**
     * 프로젝트 삭제
     *
     * @param projectId 프로젝트 ID
     * @return message
     */
    @DeleteMapping("/projects/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> deleteProject(@PathVariable Long projectId) {
        service.deleteProject(projectId);

        return ApiResult.success("프로젝트 삭제에 성공했습니다.");
    }

    /**
     * 내 프로젝트 조회
     *
     * @param userId 사용자 ID
     * @return message, projectList
     */
    @GetMapping("/users/me/projects/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetMyProjectsResponse> getMyProjects(@PathVariable Long userId) {
        return ApiResult.success("내 프로젝트 조회에 성공했습니다.", service.getMyProjects(userId));
    }

    /**
     * 후원자들의 배송 정보 목록 조회
     *
     * @param projectId 프로젝트 ID
     * @return message, shippingInfoList
     */
    @GetMapping("/projects/{projectId}/shipping-infos")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetShippingInfosResponse> getShippingInfos(@PathVariable Long projectId) {
        return ApiResult.success("배송 정보 조회에 성공했습니다.", service.getShippingInfos(projectId));
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
    public ApiResult<Void> patchProjectStatus(
            @PathVariable Long projectId,
            @Valid @RequestBody PatchProjectStatusRequest request
    ) {
        service.patchProjectStatus(projectId, request);

        return ApiResult.success("프로젝트 상태 변경에 성공했습니다.");
    }
}