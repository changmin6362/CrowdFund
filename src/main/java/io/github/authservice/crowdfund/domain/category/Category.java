package io.github.authservice.crowdfund.domain.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("category")
public record Category(
    @Id Long id,
    Long parentId,
    String name,
    Integer level
) {}
