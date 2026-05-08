package io.github.authservice.crowdfund.feature.reward.request;

import jakarta.validation.constraints.Min;

/**
 * 리워드 수정 요청 데이터
 *
 * title : 리워드 제목
 * description : 리워드 설명
 * price : 리워드 가격
 * stock : 리워드 재고
 */
public record ModifyRequest(

        String title,

        String description,

        @Min(0)
        Integer price,

        @Min(1)
        Integer stock

) {
}
