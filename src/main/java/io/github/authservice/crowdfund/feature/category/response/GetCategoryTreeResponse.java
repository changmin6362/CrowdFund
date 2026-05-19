package io.github.authservice.crowdfund.feature.category.response;

import java.util.List;

public record GetCategoryTreeResponse(
        String message,
        List<CategoryNode> categoryTree
) {
}
