package io.github.crowdfund.domain.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 카테고리 테이블 매핑용 엔티티
 */
@Table("category")
public record Category(
    @Id Integer id,
    Integer parentId,
    String name,
    Integer depth,
    Integer sortOrder,
    boolean isActive
) {
}
