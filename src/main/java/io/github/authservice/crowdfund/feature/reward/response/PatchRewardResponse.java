package io.github.authservice.crowdfund.feature.reward.response;

public record PatchRewardResponse(
        String message,
        RewardInfo patchedReward
) {
}