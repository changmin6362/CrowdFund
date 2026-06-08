package io.github.crowdfund.feature.payment.dto.history;

import io.github.crowdfund.domain.payment.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PaymentHistoryInfo(
        @Schema(description = "결제 이력 ID", example = "1")
        Long historyId,

        @Schema(description = "변경된 결제 상태")
        PaymentStatus status,

        @Schema(description = "상태 변경 시각", example = "2023-09-01T12:00:00")
        LocalDateTime changedAt,

        @Schema(description = "상태 변경 사유", example = "최초 결제 완료")
        String reason,

        @Schema(description = "PG 트랜잭션 ID", example = "PG-TX-12345")
        String pgTransactionId
) {
}
