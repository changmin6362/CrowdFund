package io.github.crowdfund.feature.payment.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record PaymentDetailResponse(
        @Schema(description = "결제 상세 정보")
        PaymentDetail paymentDetail
) {
}
