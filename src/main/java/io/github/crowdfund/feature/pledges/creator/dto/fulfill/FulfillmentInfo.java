package io.github.crowdfund.feature.pledges.creator.dto.fulfill;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import java.time.LocalDateTime;

public record FulfillmentInfo(
        Long pledgeId,
        FulfillmentStatus fulfillmentStatus,
        LocalDateTime fulfilledAt
) {
}
