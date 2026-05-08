package io.github.authservice.crowdfund.feature.reward.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

/**
 * 리워드 생성 요청 데이터
 *
 * @param title 제목
 * @param description 설명
 * @Min() - 최소값 검증
 * @param price 가격
 * @param stock 재고
 */
public record AddRequest(
        @NotBlank
        String title,
        String description,
        @NotNull
        @Min(0)
        Integer price,
        @NotNull
        @Min(1)
        Integer stock
) {
    }
