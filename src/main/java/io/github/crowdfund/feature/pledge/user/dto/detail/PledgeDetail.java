package io.github.crowdfund.feature.pledge.user.dto.detail;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record PledgeDetail(
        @Schema(description = "후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "생성 일시", example = "2023-09-15T12:00:00")
        String createdAt,

        @Schema(description = "후원 상태", example = "FULFILLED")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "프로젝트 제목", example = "새로운 프로젝트")
        String projectTitle,

        @Schema(description = "후원 금액", example = "10000.00")
        BigDecimal amount,

        @Schema(description = "결제 방법", example = "카드")
        String paymentMethod,

        @Schema(description = "리워드 이름", example = "우수 후원자 상품")
        String rewardName,

        @Schema(description = "배송 주소 정보")
        ShippingAddress shippingAddress
) {
}
