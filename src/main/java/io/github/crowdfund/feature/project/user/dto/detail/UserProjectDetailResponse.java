package io.github.crowdfund.feature.project.user.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserProjectDetailResponse(
        @Schema(description = "프로젝트 상세 정보")
        ProjectDetail projectDetail
) {
}