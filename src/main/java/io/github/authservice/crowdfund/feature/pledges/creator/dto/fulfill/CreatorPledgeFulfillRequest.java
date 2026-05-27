package io.github.authservice.crowdfund.feature.pledges.creator.dto.fulfill;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import jakarta.validation.constraints.NotNull;

public record CreatorPledgeFulfillRequest(
        @NotNull(message = "이행 상태는 필수 입력 항목입니다.")
        FulfillmentStatus fulfillmentStatus
) {
}
