package io.github.crowdfund.feature.pledge.my.dto.fetch;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MyPledgeInfo(
        @Schema(description = "후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "프로젝트 ID", example = "2")
        Long projectId,

        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        String projectTitle,

        @Schema(description = "보상 ID", example = "1")
        Long rewardId,

        @Schema(description = "보상명", example = "리워드 제목 예시")
        String rewardTitle,

        @Schema(description = "후원 금액", example = "35000")
        BigDecimal amount,

        @Schema(description = "후원 상태")
        PledgeStatus status,

        @Schema(description = "보상 이행 상태")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "후원 일시", example = "2023-08-01T12:00:00")
        LocalDateTime pledgedAt
) {
}
