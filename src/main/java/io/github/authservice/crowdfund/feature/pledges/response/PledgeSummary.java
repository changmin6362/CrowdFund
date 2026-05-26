package io.github.authservice.crowdfund.feature.pledges.response;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;

public record PledgeSummary(
        Long id,
        Long userId,
        String userName,
        Long projectId,
        String projectTitle,
        Long rewardId,
        Long amount,
        FulfillmentStatus fulfillmentStatus,
        String createdAt
) {
}
