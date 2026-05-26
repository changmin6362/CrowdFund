package io.github.authservice.crowdfund.feature.payment.response;

public record GetPaymentResponse(
        String message,
        PaymentDetail paymentDetail
) {
}
