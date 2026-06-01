package io.github.crowdfund.feature.payment.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentDetail(
        @Schema(description = "결제 ID", example = "1")
        Long paymentId,

        @Schema(description = "후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "결제 방법", example = "카드")
        String paymentMethod,

        @Schema(description = "결제 금액", example = "35000")
        BigDecimal amount,

        @Schema(description = "결제 상태", example = "완료")
        String status,

        @Schema(description = "결제 일시", example = "2023-09-01T12:00:00")
        LocalDateTime paidAt,

        @Schema(description = "생성 일시", example = "2023-09-01T12:00:00")
        LocalDateTime createdAt
) {
}
