package io.github.crowdfund.feature.pledge.admin.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record AdminPaymentDetail(
        @Schema(description = "후원 금액", example = "10000.00")
        BigDecimal amount,

        @Schema(description = "결제 방법", example = "카드")
        String paymentMethod
) {
}
