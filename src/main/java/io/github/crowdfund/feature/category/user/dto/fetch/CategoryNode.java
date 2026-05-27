package io.github.crowdfund.feature.category.user.dto.fetch;

import java.util.List;

public record CategoryNode(
        Integer id,
        String name,
        int depth,
        int sortOrder,
        List<CategoryNode> children
) {
}
