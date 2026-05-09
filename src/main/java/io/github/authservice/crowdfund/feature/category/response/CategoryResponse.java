package io.github.authservice.crowdfund.feature.category.response;



public record CategoryResponse(
        Long id, String name, Long parentId, Integer sortOrder, Integer level, boolean isActive
) {}
