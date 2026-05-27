package io.github.authservice.crowdfund.feature.reward.creator.dto.create;

import io.github.authservice.crowdfund.feature.reward.creator.dto.RewardInfo;

public record CreatorRewardCreateResponse(
        RewardInfo createdReward
) {
}