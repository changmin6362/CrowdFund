package io.github.crowdfund.feature.pledge.creator.dto.fulfill;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record FulfillmentInfo(
        @Schema(description = "보상 ID", example = "1")
        Long pledgeId,

        @Schema(description = "보상 이행 상태", example = "FULFILLED")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "보상 이행 일시", example = "2023-09-20T12:34:56")
        LocalDateTime fulfilledAt
) {
}
