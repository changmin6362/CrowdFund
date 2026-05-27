package io.github.crowdfund.feature.category.user.dto.fetch;

import java.util.List;

public record UserFetchCategoryResponse(
        List<CategoryNode> categoryTree
) {
}
