package io.github.crowdfund.feature.project.creator.dto.extract;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShippingInfo(
        @Schema(description = "배송지 ID")
        Long addressId,

        @Schema(description = "유저 ID")
        Long userId,

        @Schema(description = "수령인 이름")
        String recipientName,

        @Schema(description = "수령인 전화번호")
        String phone,

        @Schema(description = "우편번호")
        String postalCode,

        @Schema(description = "주소(본번)")
        String addressMain,

        @Schema(description = "주소(상세)")
        String addressDetail
) {
}
