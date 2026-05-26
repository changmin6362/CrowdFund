package io.github.authservice.crowdfund.feature.payment.response;

import java.time.LocalDateTime;

public record PaymentDetail(
        Long id,
        Long pledgeId,
        String paymentMethod,
        Long amount,
        String status,
        LocalDateTime paidAt,
        LocalDateTime createdAt
) {
}
