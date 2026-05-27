package io.github.crowdfund.feature.pledges.admin.dto.detail;

public record AdminPaymentDetail(
        Long amount,
        String paymentMethod
) {
}
