package io.github.crowdfund.feature.pledge.my.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyPledgeCreateResponse(
        @Schema(description = "후원 ID", example = "1")
        Long pledgeId
) {
}
