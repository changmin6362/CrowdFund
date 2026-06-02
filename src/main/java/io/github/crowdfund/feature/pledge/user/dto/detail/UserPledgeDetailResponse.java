package io.github.crowdfund.feature.pledge.user.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserPledgeDetailResponse(
        @Schema(description = "후원 상세 정보")
        UserPledgeDetail userPledgeDetail
) {
}
