package io.github.crowdfund.feature.pledges.creator.dto.fulfill;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreatorPledgeFulfillResponse(
        @Schema(description = "변경된 보상 이행 상태 정보")
        FulfillmentInfo fulfillment
) {
}
