package io.github.authservice.crowdfund.feature.pledges.response;

public record PledgeDetail(
        Long id,
        Long userId,
        Long projectId,
        Long rewardId,
        Long amount,
        String createdAt
) {
}
