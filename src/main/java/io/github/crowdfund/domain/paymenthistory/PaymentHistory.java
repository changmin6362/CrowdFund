package io.github.crowdfund.domain.paymenthistory;

import io.github.crowdfund.domain.payment.PaymentStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 결제 상태 변경 이력 테이블 매핑용 엔티티
 *
 * @param id                히스토리 ID
 * @param paymentId         결제 ID
 * @param status            변경된 결제 상태 [PENDING: 결제 대기중, PAID: 결제 완료, FAILED: 결제 실패, CANCELED: 결제 취소, REFUNDED: 환불]
 * @param changedAt         상태 변경 시각 (실패, 취소, 환불 등)
 * @param reason            상태 변경 사유
 * @param pgTransactionId   PG 트랜잭션 ID
 */
@Table("payment_history")
public record PaymentHistory(
        @Id Long id,
        Long paymentId,
        PaymentStatus status,
        LocalDateTime changedAt,
        String reason,
        String pgTransactionId
) {
}
