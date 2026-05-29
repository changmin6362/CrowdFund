package io.github.crowdfund.feature.payment.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PaymentDetail(
        @Schema(description = "결제 ID")
        Long id,

        @Schema(description = "후원 ID")
        Long pledgeId,

        @Schema(description = "결제 방법")
        String paymentMethod,

        @Schema(description = "결제 금액")
        Long amount,

        @Schema(description = "결제 상태")
        String status,

        @Schema(description = "결제 일시")
        LocalDateTime paidAt,

        @Schema(description = "생성 일시")
        LocalDateTime createdAt
) {
}
