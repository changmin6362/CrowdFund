package io.github.crowdfund.feature.project.user.dto.fetch;

import io.github.crowdfund.domain.project.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectElement(
        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "프로젝트 생성자 ID", example = "1")
        Long creatorId,

        @Schema(description = "프로젝트 생성자 닉네임", example = "닉네임")
        String creatorNickname,

        @Schema(description = "프로젝트 카테고리 ID", example = "1")
        Integer categoryId,

        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        String title,

        @Schema(description = "프로젝트 목표 금액", example = "1000000")
        BigDecimal goalAmount,

        @Schema(description = "프로젝트 현재 금액", example = "500000")
        BigDecimal currentAmount,

        @Schema(description = "달성률 (%)", example = "50")
        Integer achievementRate,

        @Schema(description = "남은 기간 (일)", example = "10")
        Long remainingDays,

        @Schema(description = "프로젝트 종료 시간", example = "2023-12-31T23:59:59")
        LocalDateTime endAt,

        @Schema(description = "프로젝트 상태")
        ProjectStatus status,

        @Schema(description = "프로젝트 생성 시간", example = "2023-08-01T12:00:00")
        LocalDateTime createdAt
) {
}
