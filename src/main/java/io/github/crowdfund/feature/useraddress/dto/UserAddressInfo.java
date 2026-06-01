package io.github.crowdfund.feature.useraddress.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record UserAddressInfo(
        @Schema(description = "배송지 ID", example = "1")
        Long addressId,

        @Schema(description = "수령인 이름", example = "김공자")
        String recipientName,

        @Schema(description = "전화번호", example = "010-1234-5678")
        String phone,

        @Schema(description = "우편번호", example = "12345")
        String postalCode,

        @Schema(description = "기본 주소", example = "서울시 중구")
        String addressMain,

        @Schema(description = "상세 주소", example = "관철동 123-45")
        String addressDetail,

        @Schema(description = "기본 배송지 여부", example = "true")
        boolean isDefault,

        @Schema(description = "생성 일시", example = "2023-09-01T12:34:56")
        LocalDateTime createdAt,

        @Schema(description = "수정 일시", example = "2023-09-01T12:34:56")
        LocalDateTime updatedAt
) {
}
