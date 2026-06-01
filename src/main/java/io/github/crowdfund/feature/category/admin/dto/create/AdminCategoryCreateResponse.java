package io.github.crowdfund.feature.category.admin.dto.create;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "카테고리 생성 응답")
public record AdminCategoryCreateResponse(
        @Schema(description = "생성된 카테고리 정보")
        CategoryInfo category
) {
}