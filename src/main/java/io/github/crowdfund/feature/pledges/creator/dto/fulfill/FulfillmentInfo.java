package io.github.crowdfund.feature.pledges.creator.dto.fulfill;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record FulfillmentInfo(
        @Schema(description = "보상 ID")
        Long pledgeId,

        @Schema(description = "보상 이행 상태")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "보상 이행 일시")
        LocalDateTime fulfilledAt
) {
}
