package io.github.crowdfund.feature.category.admin.dto.reorder;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AdminCategoryReorderRequest(
        @NotEmpty(message = "변경할 카테고리 목록이 비어있습니다.")
        @Valid
        List<CategorySortItem> categories
) {
    public record CategorySortItem(
            @NotNull(message = "카테고리 ID는 필수입니다.")
            Integer id,

            @NotNull(message = "정렬 순서는 필수입니다.")
            Integer sortOrder
    ) {
    }
}
