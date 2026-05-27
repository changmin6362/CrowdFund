package io.github.crowdfund.feature.reward.creator.dto.update;

import io.github.crowdfund.feature.reward.creator.dto.RewardInfo;

public record CreatorRewardUpdateResponse(
        RewardInfo patchedReward
) {
}