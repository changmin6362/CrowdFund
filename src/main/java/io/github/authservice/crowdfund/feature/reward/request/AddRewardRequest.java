package io.github.authservice.crowdfund.feature.reward.request;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * 리워드 생성 요청 데이터
 *
 * @param title 제목
 * @param description 설명
 * @Min() - 최소값 검증
 * @param price 가격
 * @param stock 재고
 */
public record AddRewardRequest(
        @NotBlank (message = "제목은 필수 입력 항목입니다.")
        String title,
        String description,
        @NotNull (message = "가격은 필수입니다.")
        @Min (value = 0, message = "가격은 0원으로 설정할 수 없습니다.")
        BigDecimal price,
        @NotNull
        @Min(value = 1, message = "재고는 1개 이상이어야 합니다.")
        Integer stock
) {
}