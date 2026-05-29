package io.github.crowdfund.feature.category.admin.dto.reorder;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "카테고리 정렬 순서 변경 요청")
public record AdminCategoryReorderRequest(
        @Schema(description = "변경할 카테고리 목록")
        @NotEmpty(message = "변경할 카테고리 목록이 비어있습니다.")
        @Valid
        List<CategorySortItem> categories
) {
}
