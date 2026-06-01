package io.github.crowdfund.feature.pledge.admin.dto.fetch;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record PledgeSummary(
        @Schema(description = "후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "유저 ID", example = "1")
        Long userId,

        @Schema(description = "유저 이름", example = "김공자")
        String userName,

        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        String projectTitle,

        @Schema(description = "리워드 ID", example = "1")
        Long rewardId,

        @Schema(description = "후원 금액", example = "35000")
        BigDecimal amount,

        @Schema(description = "후원 상태")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "생성 일시", example = "2023-08-01T12:00:00")
        String createdAt
) {
}
