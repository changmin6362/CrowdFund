package io.github.authservice.crowdfund.domain.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("category")
public record Category(
    @Id Integer id,
    Integer parentId,
    String name,
    Integer depth,
    Integer sortOrder,
    boolean isActive
) {}
