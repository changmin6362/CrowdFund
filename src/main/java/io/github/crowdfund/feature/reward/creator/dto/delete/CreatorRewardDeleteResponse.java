package io.github.crowdfund.feature.reward.creator.dto.delete;

import io.swagger.v3.oas.annotations.media.Schema;

public record CreatorRewardDeleteResponse(
        @Schema(description = "삭제된 리워드 ID", example = "1")
        Long deletedRewardId
) {
}