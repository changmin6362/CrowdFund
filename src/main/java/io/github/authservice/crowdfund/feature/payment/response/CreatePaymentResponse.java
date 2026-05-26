package io.github.authservice.crowdfund.feature.payment.response;

public record CreatePaymentResponse(
        String message,
        Long paymentId
) {
}
