package io.github.crowdfund.feature.pledge.my.dto.detail;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record MyPledgeDetail(
        @Schema(description = "후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "생성 일시", example = "2023-09-15T12:00:00")
        String createdAt,

        @Schema(description = "후원 상태")
        PledgeStatus status,

        @Schema(description = "보상 이행 상태")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "프로젝트 제목", example = "새로운 프로젝트")
        String projectTitle,

        @Schema(description = "후원 금액", example = "10000.00")
        BigDecimal amount,

        @Schema(description = "리워드 이름", example = "1등급 후원자 상품")
        String rewardName
) {
}
