package io.github.authservice.crowdfund.domain.comment;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;

@Table("comment")
public record Comment(
    @Id Long id,
    Long userId,
    Long projectId,
    String content,
    LocalDateTime createdAt
) {}
