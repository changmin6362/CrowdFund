package io.github.crowdfund.feature.category.admin.dto.move;

import jakarta.validation.constraints.Positive;

public record AdminCategoryMoveRequest(
        @Positive(message = "상위 카테고리 ID는 양수여야 합니다.")
        Integer parentId
) {
}
