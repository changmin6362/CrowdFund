package io.github.crowdfund.feature.pledge.admin.dto.detail;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;

public record AdminPledgeDetail(
        Long pledgeId,
        String createdAt,
        FulfillmentStatus fulfillmentStatus,
        AdminUserDetail user,
        AdminPaymentDetail payment,
        AdminProjectDetail project
) {
}
