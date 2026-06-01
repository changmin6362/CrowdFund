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
 * @param fulfillmentStatus 보상 이행 상태 [READY: 준비중, COMPLETED: 지급 완료]
 * @param fulfilledAt       보상 이행 완료 일시
 * @param createdAt         후원일시
 */
@Table("pledge")
public record Pledge(
    @Id Long id,
    Long userId,
    Long projectId,
    Long rewardId,
    BigDecimal amount,
    FulfillmentStatus fulfillmentStatus,
    LocalDateTime fulfilledAt,
    LocalDateTime createdAt
) {}
