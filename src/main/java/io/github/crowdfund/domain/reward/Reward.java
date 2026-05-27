package io.github.crowdfund.domain.reward;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 리워드 테이블 매핑용 엔티티
 *
 * @param id          보상 ID
 * @param projectId   프로젝트 ID
 * @param title       보상명 (최대 30자)
 * @param description 보상 설명
 * @param price      후원 금액
 * @param stock       수량
 * @param createdAt   생성일시
 */
@Table("reward")
public record Reward(
    @Id Long id,
    Long projectId,
    String title,
    String description,
    BigDecimal price,
    Integer stock,
    LocalDateTime createdAt
) {
}
