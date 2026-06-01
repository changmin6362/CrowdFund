package io.github.crowdfund.feature.pledge.user.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShippingAddress(
        @Schema(description = "수령인 이름", example = "김공자")
        String recipientName,

        @Schema(description = "수령인 전화번호", example = "010-1234-5678")
        String recipientPhone,

        @Schema(description = "배송 주소", example = "서울시 중구")
        String addressMain,

        @Schema(description = "상세 배송 주소", example = "신당동 123-45")
        String addressDetail,

        @Schema(description = "우편 번호", example = "06060")
        String postalCode
) {
}
