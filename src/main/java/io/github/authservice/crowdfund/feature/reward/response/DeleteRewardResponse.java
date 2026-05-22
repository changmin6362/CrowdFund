package io.github.authservice.crowdfund.feature.reward.response;

public record DeleteRewardResponse(
        String message,
        Long deletedRewardId
) {
}