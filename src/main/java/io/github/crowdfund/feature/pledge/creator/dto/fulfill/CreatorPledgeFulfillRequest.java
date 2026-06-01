package io.github.crowdfund.feature.pledge.creator.dto.fulfill;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreatorPledgeFulfillRequest(
        @Schema(description = "보상 이행 상태")
        @NotNull(message = "이행 상태는 필수 입력 항목입니다.")
        FulfillmentStatus fulfillmentStatus
) {
}
