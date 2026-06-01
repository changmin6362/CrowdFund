package io.github.crowdfund.feature.pledgeaddress.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record PledgeAddressInfo(
        @Schema(description = "참여한 후원의 배송 정보 ID", example = "1")
        Long pledgeAddressId,

        @Schema(description = "참여한 후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "참여한 후원의 배송 정보를 등록한 사용자 ID", example = "1")
        Long userId,

        @Schema(description = "받는 사람 이름", example = "김공자")
        String recipientName,

        @Schema(description = "받는 사람 전화번호", example = "010-1234-5678")
        String phone,

        @Schema(description = "받는 사람 우편번호", example = "12345")
        String postalCode,

        @Schema(description = "받는 사람 주소(주소지)", example = "서울시 중구")
        String addressMain,

        @Schema(description = "받는 사람 주소(상세주소)", example = "서울시 중구 서소문로 22")
        String addressDetail,

        @Schema(description = "참여한 후원의 배송 정보 등록 일시", example = "2023-09-15T12:00:00")
        LocalDateTime createdAt,

        @Schema(description = "참여한 후원의 배송 정보 수정 일시", example = "2023-09-15T12:00:00")
        LocalDateTime updatedAt
) {
}
