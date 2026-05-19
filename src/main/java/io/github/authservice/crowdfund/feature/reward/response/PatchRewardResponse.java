package io.github.authservice.crowdfund.feature.reward.response;

import java.util.List;

public record PatchRewardResponse(
        String message,
        RewardInfo reward
) {
}