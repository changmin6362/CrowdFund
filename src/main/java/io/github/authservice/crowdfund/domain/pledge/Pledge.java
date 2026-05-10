package io.github.authservice.crowdfund.domain.pledge;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("pledge")
public record Pledge(
    @Id Long id,
    Long userId,
    Long projectId,
    Long rewardId,
    Long amount
) {}
