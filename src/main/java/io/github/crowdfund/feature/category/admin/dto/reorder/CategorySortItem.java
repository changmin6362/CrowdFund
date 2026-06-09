package io.github.crowdfund.feature.category.admin.dto.reorder;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "카테고리 정렬 정보")
public record CategorySortItem(
        @Schema(description = "카테고리 ID", example = "1")
        @NotNull(message = "카테고리 ID는 필수입니다.")
        @JsonProperty("categoryId")
        Integer categoryId,

        @Schema(description = "변경할 정렬 순서", example = "25")
        @NotNull(message = "정렬 순서는 필수입니다.")
        @JsonProperty("sortOrder")
        Integer sortOrder
) {
}
