package io.github.crowdfund.domain.payment;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 결제 테이블 매핑용 엔티티
 *
 * @param id             결제 ID
 * @param pledgeId       후원 ID
 * @param paymentMethod  결제 수단 (최대 50자)
 * @param amount         결제 금액
 * @param status         결제 상태 (최대 20자)
 * @param paidAt         결제 완료 시간
 * @param createdAt      생성일시
 */
@Table("payment")
public record Payment(
    @Id Long id,
    Long pledgeId,
    String paymentMethod,
    BigDecimal amount,
    String status,
    LocalDateTime paidAt,
    LocalDateTime createdAt
) {}
