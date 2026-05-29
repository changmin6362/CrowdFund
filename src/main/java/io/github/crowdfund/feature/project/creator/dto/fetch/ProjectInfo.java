package io.github.crowdfund.feature.project.creator.dto.fetch;

import io.github.crowdfund.domain.project.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectInfo(
        @Schema(description = "프로젝트 ID")
        Long projectId,

        @Schema(description = "프로젝트 제목")
        String title,

        @Schema(description = "목표 금액")
        BigDecimal goalAmount,

        @Schema(description = "현재 금액")
        BigDecimal currentAmount,

        @Schema(description = "마감일")
        LocalDateTime endAt,

        @Schema(description = "프로젝트 상태")
        ProjectStatus status
) {}
