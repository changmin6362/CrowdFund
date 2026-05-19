package io.github.authservice.crowdfund.feature.category.response;

import java.util.List;

public record GetCategoryTreeResponse(
        String message,
        List<CategoryNode> categoryTree
) {
    public record CategoryNode(
            Integer id,
            String name,
            int depth,
            int sortOrder,
            List<CategoryNode> children
    ) {
    }
}
