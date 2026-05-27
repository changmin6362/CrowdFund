package io.github.authservice.crowdfund.feature.pledges.admin.dto.detail;

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
