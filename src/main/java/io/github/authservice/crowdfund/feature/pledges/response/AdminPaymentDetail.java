package io.github.authservice.crowdfund.feature.pledges.response;

public record AdminPaymentDetail(
        Long amount,
        String paymentMethod
) {
}
