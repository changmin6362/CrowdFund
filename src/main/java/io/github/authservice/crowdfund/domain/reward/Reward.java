package io.github.authservice.crowdfund.domain.reward;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("reward")
public record Reward(
    @Id Long id,
    Long projectId,
    String title,
    String description,
    Long amount,
    Integer stock
) {}
