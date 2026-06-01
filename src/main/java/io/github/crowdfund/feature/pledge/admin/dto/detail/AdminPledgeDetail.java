package io.github.crowdfund.feature.pledge.admin.dto.detail;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record AdminPledgeDetail(
        @Schema(description = "후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "생성 일시", example = "2023-08-01T12:00:00")
        String createdAt,

        @Schema(description = "후원 상태")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "유저 정보")
        AdminUserDetail user,

        @Schema(description = "결제 정보")
        AdminPaymentDetail payment,

        @Schema(description = "프로젝트 정보")
        AdminProjectDetail project
) {
}
