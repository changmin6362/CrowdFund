package io.github.crowdfund.feature.category.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record CategoryTreeNode(
        @Schema(description = "카테고리 ID", example = "1")
        Integer categoryId,

        @Schema(description = "카테고리 이름", example = "게임")
        String name,

        @Schema(description = "카테고리 깊이", example = "0")
        int depth,

        @Schema(description = "카테고리 순서", example = "10")
        int sortOrder,

        @Schema(
                description = "하위 카테고리 목록",
                type = "string",
                example = "[하위 카테고리 객체 배열 (CategoryTreeNode 구조 반복)]"
        )
        List<CategoryTreeNode> children
) {
}
