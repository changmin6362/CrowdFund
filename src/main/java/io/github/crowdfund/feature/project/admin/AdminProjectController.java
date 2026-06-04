package io.github.crowdfund.feature.project.admin;

import io.github.crowdfund.feature.project.admin.dto.update.AdminProjectUpdateRequest;
import io.github.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/projects")
@Validated
@RequiredArgsConstructor
@Tag(name = "05. Project - Admin", description = "관리자용 프로젝트 API")
public class AdminProjectController {

    private final AdminProjectService service;

    /**
     * 프로젝트의 상태 갱신
     *
     * @param projectId 프로젝트 식별 번호
     * @param request   상태 변경 요청 데이터
     * @return UpdateProjectResponse 변경 성공 결과
     */
    @Operation(summary = "프로젝트 상태 변경")
    @ApiResponse(responseCode = "200", description = "프로젝트 상태 변경 성공 응답 예시")
    @PatchMapping("/{projectId}/status")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> update(
            @PathVariable Long projectId,
            @Valid @RequestBody AdminProjectUpdateRequest request
    ) {
        service.update(projectId, request);

        return ApiResult.success("프로젝트 상태 변경에 성공했습니다.");
    }
}