package io.github.authservice.crowdfund.feature.reward.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 리워드 수정 요청 데이터
 *
 * title : 리워드 제목
 * description : 리워드 설명
 * price : 리워드 가격
 * stock : 리워드 재고
 */
public record ModifyRequest(

        @NotBlank (message = "제목은 필수 입력 항목입니다.")
        String title,

        @NotBlank (message = "내용은 필수 입력 항목입니다.")
        String description,

        @NotNull
        @Min(value = 0, message = "가격은 0원일 수 없습니다.")
        Integer price,

        @NotNull
        @Min(value = 1 , message = "재고는 1개 이상이어야 합니다.")
        Integer stock

) {
}