package io.github.crowdfund.feature.pledgeaddress.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PledgeAddressInfo(
        @Schema(description = "참여한 후원의 배송 정보 ID")
        Long pledgeAddressId,

        @Schema(description = "참여한 후원 ID")
        Long pledgeId,

        @Schema(description = "참여한 후원의 배송 정보를 등록한 사용자 ID")
        Long userId,

        @Schema(description = "받는 사람 이름")
        String recipientName,

        @Schema(description = "받는 사람 전화번호")
        String phone,

        @Schema(description = "받는 사람 우편번호")
        String postalCode,

        @Schema(description = "받는 사람 주소(주소지)")
        String addressMain,

        @Schema(description = "받는 사람 주소(상세주소)")
        String addressDetail,

        @Schema(description = "참여한 후원의 배송 정보 등록 일시")
        LocalDateTime createdAt,

        @Schema(description = "참여한 후원의 배송 정보 수정 일시")
        LocalDateTime updatedAt
) {
}
