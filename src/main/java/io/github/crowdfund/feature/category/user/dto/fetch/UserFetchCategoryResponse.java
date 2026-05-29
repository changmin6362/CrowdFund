package io.github.crowdfund.feature.category.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "카테고리 트리 조회 응답")
public record UserFetchCategoryResponse(
        @Schema(description = "카테고리 트리")
        List<CategoryTreeNode> categoryTree
) {
}
