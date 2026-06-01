package io.github.crowdfund.feature.pledge.user.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserPledgeCreateResponse(
        @Schema(description = "후원 ID", example = "1")
        Long pledgeId
) {
}
