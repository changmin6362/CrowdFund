package io.github.crowdfund.feature.reward.creator.dto.create;

import io.github.crowdfund.feature.reward.creator.dto.RewardInfo;

public record CreatorRewardCreateResponse(
        RewardInfo createdReward
) {
}