package io.github.authservice.crowdfund.domain.project;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("project")
public record Project(
    @Id Long id,
    Long creatorId,
    String title,
    String description,
    Long goalAmount,
    LocalDateTime startAt,
    LocalDateTime endAt,
    Long categoryId,
    String status
) {}
