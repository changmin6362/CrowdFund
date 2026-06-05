package io.github.crowdfund.feature.reward.creator.dto.update;

import io.github.crowdfund.feature.reward.creator.dto.RewardInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreatorRewardUpdateResponse(
        @Schema(description = "수정된 리워드 정보")
        RewardInfo updatedReward
) {
}