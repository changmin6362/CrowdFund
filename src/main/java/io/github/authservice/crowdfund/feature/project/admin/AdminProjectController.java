package io.github.authservice.crowdfund.feature.project.admin;

import io.github.authservice.crowdfund.feature.project.admin.dto.update.AdminProjectUpdateRequest;
import io.github.authservice.crowdfund.global.common.ApiResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Project", description = "프로젝트 관련 API")
@RestController
@RequestMapping("/api/admin/projects")
@Validated
@RequiredArgsConstructor
public class AdminProjectController {

    private final AdminProjectService service;

    /**
     * 프로젝트의 상태 갱신
     *
     * @param projectId 프로젝트 식별 번호
     * @param request   상태 변경 요청 데이터
     * @return UpdateProjectResponse 변경 성공 결과
     */
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