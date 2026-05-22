package io.github.authservice.crowdfund.feature.reward.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PatchRewardReqeust(

        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        String title,

        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        String description,

        @NotNull
        @Min(value = 0, message = "가격은 0원일 수 없습니다.")
        BigDecimal price,

        @NotNull
        @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
        Integer stock

) {
}