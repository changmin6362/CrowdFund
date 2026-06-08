package io.github.crowdfund.feature.pledge.my.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShippingAddress(
        @Schema(description = "후원의 배송 주소 ID", example = "1")
        Long pledgeAddressId,

        @Schema(description = "수령인 이름", example = "김공자")
        String recipientName,

        @Schema(description = "수령인 전화번호", example = "010-1234-5678")
        String recipientPhone,

        @Schema(description = "기본 주소", example = "서울시 중구")
        String addressMain,

        @Schema(description = "상세 주소", example = "신당동 123-45")
        String addressDetail,

        @Schema(description = "우편 번호", example = "06060")
        String postalCode,

        @Schema(description = "생성 일시", example = "2023-09-15T12:00:00")
        String createdAt,

        @Schema(description = "수정 일시", example = "2023-09-15T12:00:00")
        String updatedAt
) {
}
