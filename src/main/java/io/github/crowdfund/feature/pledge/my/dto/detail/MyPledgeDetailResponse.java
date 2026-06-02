package io.github.crowdfund.feature.pledge.my.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyPledgeDetailResponse(
        @Schema(description = "후원 상세 정보")
        MyPledgeDetail myPledgeDetail
) {
}
