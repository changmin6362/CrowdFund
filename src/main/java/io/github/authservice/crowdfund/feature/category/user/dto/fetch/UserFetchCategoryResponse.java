package io.github.authservice.crowdfund.feature.category.user.dto.fetch;

import java.util.List;

public record UserFetchCategoryResponse(
        List<CategoryNode> categoryTree
) {
}
