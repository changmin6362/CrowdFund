package io.github.authservice.crowdfund.feature.category.response;

public record CategoryResponse(
        Long id,
        String name,
        Integer level,
        Integer sortOrder,
        Long parentId,   // 부모가 없으면 null
        boolean isActive
) {
}
