package io.github.crowdfund.feature.category.admin.dto.create;


import io.github.crowdfund.feature.category.user.dto.fetch.CategoryTreeNode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "카테고리 생성 응답")
public record AdminCategoryCreateResponse(
        @Schema(description = "생성된 카테고리 정보")
        CategoryInfo category,

        @Schema(description = "카테고리 트리")
        List<CategoryTreeNode> categoryTree
) {
}