package io.github.crowdfund.feature.project.user.dto.fetch;

import io.github.crowdfund.domain.project.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectElement(
        @Schema(description = "프로젝트 ID")
        Long projectId,

        @Schema(description = "프로젝트 생성자 ID")
        Long creatorId,

        @Schema(description = "프로젝트 카테고리 ID")
        Integer categoryId,

        @Schema(description = "프로젝트 제목")
        String title,

        @Schema(description = "프로젝트 목표 금액")
        BigDecimal goalAmount,

        @Schema(description = "프로젝트 현재 금액")
        BigDecimal currentAmount,

        @Schema(description = "프로젝트 종료 시간")
        LocalDateTime endAt,

        @Schema(description = "프로젝트 상태")
        ProjectStatus status,

        @Schema(description = "프로젝트 생성 시간")
        LocalDateTime createdAt
) {
}
