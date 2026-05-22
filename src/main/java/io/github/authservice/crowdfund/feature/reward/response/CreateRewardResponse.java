package io.github.authservice.crowdfund.feature.reward.response;

public record CreateRewardResponse(
        String message,
        RewardInfo createdReward
) {
}