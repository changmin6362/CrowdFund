package io.github.crowdfund.feature.reward.creator.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreatorRewardUpdateStockRequest(
        @Schema(description = "리워드 재고", example = "10")
        @NotNull
        @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
        Integer stock
) {
}
