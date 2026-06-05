package io.github.crowdfund.feature.payment.dto.history;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record PaymentHistoryResponse(
        @Schema(description = "결제 이력 목록")
        List<PaymentHistoryInfo> paymentHistories
) {
}
