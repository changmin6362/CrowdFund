package io.github.crowdfund.feature.project.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record NextCursor(
        @Schema(description = "프로젝트 생성 시간")
        LocalDateTime createdAt,

        @Schema(description = "프로젝트 ID")
        Long id
) {}
