package io.github.authservice.crowdfund.feature.reward.creator.dto.update;

import io.github.authservice.crowdfund.feature.reward.creator.dto.RewardInfo;

public record CreatorRewardUpdateResponse(
        RewardInfo patchedReward
) {
}