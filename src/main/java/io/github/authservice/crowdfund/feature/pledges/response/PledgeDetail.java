package io.github.authservice.crowdfund.feature.pledges.response;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;

public record PledgeDetail(
        Long pledgeId,
        String createdAt,
        FulfillmentStatus fulfillmentStatus,
        String projectTitle,
        Long amount,
        String paymentMethod,
        String rewardName,
        ShippingAddress shippingAddress
) {
}
