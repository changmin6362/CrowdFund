package io.github.crowdfund.feature.pledge.my.dto.delete;

import io.swagger.v3.oas.annotations.media.Schema;

public record MyPledgesDeleteResponse(
        @Schema(description = "삭제된 후원 ID", example = "1")
        Long deletedPledgeId
) {
}
