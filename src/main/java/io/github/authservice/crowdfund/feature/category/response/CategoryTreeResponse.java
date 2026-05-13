package io.github.authservice.crowdfund.feature.category.response;

import java.util.List;

// 트리 구조 응답
public record CategoryTreeResponse(
        String message,
        List<CategoryTree> category)
{
    public record CategoryTree(
            Long id,
            String name,
            Integer sortOrder,
            Integer level,
            List<CategoryTree> subCategories
    ) {}
}
