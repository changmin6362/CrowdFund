package io.github.authservice.crowdfund.feature.pledges.response;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import java.time.LocalDateTime;

public record UpdateFulfillmentResponse(
        String message,
        Long pledgeId,
        FulfillmentStatus fulfillmentStatus,
        LocalDateTime fulfilledAt
) {
}
