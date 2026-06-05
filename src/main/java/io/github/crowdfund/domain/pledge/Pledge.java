package io.github.crowdfund.domain.pledge;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 후원 테이블 매핑용 엔티티
 *
 * @param id                후원 ID
 * @param userId            후원한 사용자 ID
 * @param projectId         프로젝트 ID
 * @param rewardId          선택 보상 ID
 * @param amount            후원 금액
 * @param status            후원 상태 [PENDING: 대기중, PAID: 결제 완료, REFUNDED: 환불]
 * @param fulfillmentStatus 보상 이행 상태 [READY: 준비중, FULFILLED: 이행 완료]
 * @param fulfilledAt       보상 이행 완료 일시
 * @param createdAt         후원 일시
 */
@Table("pledge")
public record Pledge(@Id Long id, Long userId, Long projectId, Long rewardId, BigDecimal amount, PledgeStatus status,
                     FulfillmentStatus fulfillmentStatus, LocalDateTime fulfilledAt, LocalDateTime createdAt) {
    /**\
     * 보상 이행 완료 처리 메서드
     */
    public Pledge completeFulfillment(LocalDateTime fulfilledAt) {
        if (this.fulfillmentStatus == FulfillmentStatus.FULFILLED) {
            return this;
        }
        return new Pledge(id, userId, projectId, rewardId, amount, status, FulfillmentStatus.FULFILLED, fulfilledAt, createdAt);
    }

    /**
     * 보상 이행 취소 처리 메서드
     */
    public Pledge cancelFulfillment() {
        return new Pledge(id, userId, projectId, rewardId, amount, status, FulfillmentStatus.READY, null, createdAt);
    }

    /**
     * 후원 환불 처리 메서드
     */
    public Pledge refundPledge() {
        return new Pledge(id, userId, projectId, rewardId, amount, PledgeStatus.REFUNDED, fulfillmentStatus, fulfilledAt, createdAt);
    }

    /**
     * 결제 완료 처리 메서드
     */
    public Pledge completePayment() {
        return new Pledge(id, userId, projectId, rewardId, amount, PledgeStatus.PAID, fulfillmentStatus, fulfilledAt, createdAt);
    }

    /**
     * 후원 취소 가능 여부 확인 메서드
     */
    public boolean canCancel() {
        return this.status == PledgeStatus.PENDING && this.fulfillmentStatus == FulfillmentStatus.READY;
    }
}
