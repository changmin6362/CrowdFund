package io.github.authservice.crowdfund.feature.pledges.response;

public record PledgeSummary(
        Long id,
        Long userId,
        Long projectId,
        Long rewardId,
        Long amount,
        String createdAt
) {
}
