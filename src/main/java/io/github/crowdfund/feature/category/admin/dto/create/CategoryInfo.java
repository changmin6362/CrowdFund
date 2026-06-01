package io.github.crowdfund.feature.category.admin.dto.create;

import io.github.crowdfund.domain.category.Category;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "카테고리 정보")
public record CategoryInfo(
        @Schema(description = "카테고리 ID", example = "11")
        Integer categoryId,

        @Schema(description = "부모 카테고리 ID (최상위일 경우 null)")
        Integer parentId,

        @Schema(description = "카테고리 이름", example = "전자제품")
        String name,

        @Schema(description = "카테고리 깊이 (0부터 시작)", example = "0")
        Integer depth,

        @Schema(description = "정렬 순서", example = "10")
        Integer sortOrder,

        @Schema(description = "활성화 여부", example = "true")
        boolean isActive
) {
    public static CategoryInfo from(Category category) {
        return new CategoryInfo(
                category.id(),
                category.parentId(),
                category.name(),
                category.depth(),
                category.sortOrder(),
                category.isActive()
        );
    }
}
