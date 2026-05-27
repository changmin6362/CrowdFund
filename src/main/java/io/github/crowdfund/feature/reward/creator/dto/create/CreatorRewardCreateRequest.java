package io.github.crowdfund.feature.reward.creator.dto.create;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatorRewardCreateRequest(

        @NotBlank(message = "보상명은 필수입니다.")
        String title,

        @NotBlank(message = "보상 설명은 필수입니다.")
        String description,

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 1, message = "가격은 0원으로 설정할 수 없습니다.")
        BigDecimal price,

        @NotNull(message = "재고는 필수입니다.")
        @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
        Integer stock

) {
}