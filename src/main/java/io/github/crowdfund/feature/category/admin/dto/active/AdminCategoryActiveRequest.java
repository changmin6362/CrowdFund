package io.github.crowdfund.feature.category.admin.dto.active;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record AdminCategoryActiveRequest(
        @NotNull(message = "활성 상태는 필수입니다.")
        @Schema(description = "카테고리 활성 상태", example = "true")
        boolean isActive
) {
}
