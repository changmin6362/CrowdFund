package io.github.crowdfund.feature.pledges.admin.dto.fetch;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;

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
