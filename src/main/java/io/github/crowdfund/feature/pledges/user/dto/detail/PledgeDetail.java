package io.github.crowdfund.feature.pledges.user.dto.detail;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;

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
