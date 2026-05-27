package io.github.authservice.crowdfund.feature.pledges.creator.dto.fulfill;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import java.time.LocalDateTime;

public record FulfillmentInfo(
        Long pledgeId,
        FulfillmentStatus fulfillmentStatus,
        LocalDateTime fulfilledAt
) {
}
