package io.github.crowdfund.feature.project.user;

import io.github.crowdfund.domain.project.ProjectStatus;
import io.github.crowdfund.feature.project.user.dto.detail.UserProjectDetailResponse;
import io.github.crowdfund.feature.project.user.dto.fetch.UserProjectFetchResponse;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Project", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/api/projects")
@Validated
@RequiredArgsConstructor
public class UserProjectController {

    private final UserProjectService service;

    /**
     * 프로젝트 목록 조회 (복합 커서 기반 페이지네이션, 최신순 정렬)
     *
     * @param statuses   프로젝트 상태 필터링
     * @param categoryId 카테고리 ID 필터링
     * @return message, projectList, hasNext, nextCursor
     */
    @Operation(summary = "프로젝트 목록 조회")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserProjectFetchResponse> fetch(
            @RequestParam(required = false) List<ProjectStatus> statuses,
            @RequestParam(required = false) @Positive(message = "카테고리 ID는 양수여야 합니다.") Integer categoryId,
            @RequestParam(required = false) LocalDateTime cursorCreatedAt,
            @RequestParam(required = false) Long cursorId,
            @RequestParam(defaultValue = "10") @Positive Integer limit
    ) {
        return ApiResult.success("프로젝트 목록 조회에 성공했습니다.", service.fetch(statuses, categoryId, cursorCreatedAt, cursorId, limit));
    }

    /**
     * 프로젝트 상세 조회
     *
     * @param projectId 프로젝트 ID
     * @return message, projectDetail
     */
    @Operation(summary = "프로젝트 상세 조회")
    @GetMapping("/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserProjectDetailResponse> detail(@PathVariable Long projectId) {
        return ApiResult.success("프로젝트 상세 조회에 성공했습니다.", service.detail(projectId));
    }
}