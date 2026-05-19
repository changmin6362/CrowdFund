package io.github.authservice.crowdfund.feature.category.request;

import jakarta.validation.constraints.Positive;

public record PatchCategoryParentRequest(
        @Positive(message = "상위 카테고리 ID는 양수여야 합니다.")
        Integer parentId
) {
}
