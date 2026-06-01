package io.github.crowdfund.feature.project.creator.dto.extract;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShippingInfo(
        @Schema(description = "배송지 ID", example = "1")
        Long addressId,

        @Schema(description = "유저 ID", example = "1")
        Long userId,

        @Schema(description = "수령인 이름", example = "김공자")
        String recipientName,

        @Schema(description = "수령인 전화번호", example = "010-1234-5678")
        String phone,

        @Schema(description = "우편번호", example = "12345")
        String postalCode,

        @Schema(description = "주소(본번)", example = "서울시 중구")
        String addressMain,

        @Schema(description = "주소(상세)", example = "서울시 중구 서소문로 123")
        String addressDetail
) {
}
