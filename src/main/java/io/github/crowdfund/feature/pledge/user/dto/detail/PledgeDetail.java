package io.github.crowdfund.feature.pledge.user.dto.detail;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record PledgeDetail(
        @Schema(description = "후원 ID")
        Long pledgeId,

        @Schema(description = "생성 일시")
        String createdAt,

        @Schema(description = "후원 상태")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "프로젝트 제목")
        String projectTitle,

        @Schema(description = "후원 금액")
        BigDecimal amount,

        @Schema(description = "결제 방법")
        String paymentMethod,

        @Schema(description = "리워드 이름")
        String rewardName,

        @Schema(description = "배송 주소")
        ShippingAddress shippingAddress
) {
}
