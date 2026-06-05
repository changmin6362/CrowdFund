package io.github.crowdfund.feature.project.creator.dto.fetch;

import io.github.crowdfund.domain.project.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectInfo(
        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        String title,

        @Schema(description = "목표 금액", example = "1000000")
        BigDecimal goalAmount,

        @Schema(description = "현재 금액", example = "500000")
        BigDecimal currentAmount,

        @Schema(description = "마감일", example = "2023-12-31T23:59:59")
        LocalDateTime endAt,

        @Schema(description = "프로젝트 상태")
        ProjectStatus status
) {}
