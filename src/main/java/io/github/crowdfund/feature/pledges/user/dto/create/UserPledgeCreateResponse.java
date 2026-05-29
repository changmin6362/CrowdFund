package io.github.crowdfund.feature.pledges.user.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserPledgeCreateResponse(
        @Schema(description = "후원 ID")
        Long pledgeId
) {
}
