package io.github.crowdfund.feature.project.creator.dto.extract;

import io.swagger.v3.oas.annotations.media.Schema;

public record ShippingInfo(
        @Schema(description = "후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "유저 ID", example = "1")
        Long userId,

        @Schema(description = "후원자 이름", example = "홍길동")
        String userName,

        @Schema(description = "이메일", example = "user@example.com")
        String email,

        @Schema(description = "전화번호", example = "010-1234-5678")
        String phoneNumber,

        @Schema(description = "리워드 제목", example = "기본 리워드")
        String rewardTitle,

        @Schema(description = "이행 상태", example = "READY")
        String fulfillmentStatus
) {
}
