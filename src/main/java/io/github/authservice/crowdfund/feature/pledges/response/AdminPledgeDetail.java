package io.github.authservice.crowdfund.feature.pledges.response;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;

public record AdminPledgeDetail(
        Long pledgeId,
        String createdAt,
        FulfillmentStatus fulfillmentStatus,
        AdminUserDetail user,
        AdminPaymentDetail payment,
        AdminProjectDetail project
) {
}
