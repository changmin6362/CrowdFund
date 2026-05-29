package io.github.crowdfund.feature.payment.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

public record PaymentCreateResponse(
        @Schema(description = "결제 ID")
        Long paymentId
) {
}
