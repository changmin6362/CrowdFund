package io.github.crowdfund.feature.pledges.admin.dto.fetch;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record PledgeSummary(
        @Schema(description = "후원 ID")
        Long id,

        @Schema(description = "유저 ID")
        Long userId,

        @Schema(description = "유저 이름")
        String userName,

        @Schema(description = "프로젝트 ID")
        Long projectId,

        @Schema(description = "프로젝트 제목")
        String projectTitle,

        @Schema(description = "리워드 ID")
        Long rewardId,

        @Schema(description = "후원 금액")
        Long amount,

        @Schema(description = "후원 상태")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "생성 일시")
        String createdAt
) {
}
