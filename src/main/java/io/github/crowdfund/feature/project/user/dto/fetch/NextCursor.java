package io.github.crowdfund.feature.project.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record NextCursor(
        @Schema(description = "프로젝트 생성 시간", example = "2023-01-01T00:00:00")
        LocalDateTime createdAt,

        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId
) {}
