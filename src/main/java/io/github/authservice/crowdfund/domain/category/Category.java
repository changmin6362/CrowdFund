package io.github.authservice.crowdfund.domain.category;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 카테고리 테이블 매핑용 엔티티
 *
 * @param id         카테고리 ID
 * @param parentId   부모 카테고리 ID [이미 최상위 카테고리라면 NULL]
 * @param name       카테고리 명칭 (최대 20자)
 * @param depth      카테고리의 깊이 [예) 1: 대분류, 2: 중분류, 3: 소분류]
 * @param sortOrder  동일한 깊이 내 정렬 순서 [예) 10, 20, 30...]
 * @param isActive   활성 여부
 */
@Table("category")
public record Category(
    @Id Integer id,
    Integer parentId,
    String name,
    Integer depth,
    Integer sortOrder,
    boolean isActive
) {}
