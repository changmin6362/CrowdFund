package io.github.crowdfund.feature.useraddress.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserAddressInfo(
        @Schema(description = "배송지 ID")
        Long addressId,

        @Schema(description = "수령인 이름")
        String recipientName,

        @Schema(description = "전화번호")
        String phone,

        @Schema(description = "우편번호")
        String postalCode,

        @Schema(description = "주소(본번)")
        String addressMain,

        @Schema(description = "주소(상세)")
        String addressDetail,

        @Schema(description = "기본 배송지 여부")
        boolean isDefault,

        @Schema(description = "생성 일시")
        LocalDateTime createdAt,

        @Schema(description = "수정 일시")
        LocalDateTime updatedAt
) {
}
