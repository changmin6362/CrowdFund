package io.github.crowdfund.feature.reward.creator.dto.create;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatorRewardCreateRequest(
        @Schema(description = "리워드 제목")
        @NotBlank(message = "보상명은 필수입니다.")
        String title,

        @Schema(description = "보상 설명")
        @NotBlank(message = "보상 설명은 필수입니다.")
        String description,

        @Schema(description = "리워드 가격")
        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 1, message = "가격은 0원으로 설정할 수 없습니다.")
        BigDecimal price,

        @Schema(description = "재고 수량")
        @NotNull(message = "재고 수량은 필수입니다.")
        @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
        Integer stock

) {
}