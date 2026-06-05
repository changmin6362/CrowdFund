package io.github.crowdfund.feature.pledge.admin.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdminProjectDetail(
        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        String projectTitle
) {
}
