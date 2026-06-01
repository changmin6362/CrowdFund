package io.github.crowdfund.feature.reward.creator.dto.update;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreatorRewardUpdateReqeust(
        @Schema(description = "리워드 제목", example = "리워드 제목 예시")
        @NotBlank(message = "제목은 필수 입력 항목입니다.")
        String title,

        @Schema(description = "리워드 내용", example = "리워드 내용 예시")
        @NotBlank(message = "내용은 필수 입력 항목입니다.")
        String description,

        @Schema(description = "리워드 가격", example = "10000")
        @NotNull
        @Min(value = 0, message = "가격은 0원일 수 없습니다.")
        BigDecimal price,

        @Schema(description = "리워드 재고", example = "10")
        @NotNull
        @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
        Integer stock

) {
}