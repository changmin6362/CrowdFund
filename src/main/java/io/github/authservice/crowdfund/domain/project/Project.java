package io.github.authservice.crowdfund.domain.project;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table("project")
public record Project(
    @Id Long id,
    Integer categoryId,
    Long creatorId,
    String title,
    String description,
    BigDecimal goalAmount,
    BigDecimal currentAmount,
    LocalDateTime startAt,
    LocalDateTime endAt,
    ProjectStatus status,
    LocalDateTime createdAt
) {}
