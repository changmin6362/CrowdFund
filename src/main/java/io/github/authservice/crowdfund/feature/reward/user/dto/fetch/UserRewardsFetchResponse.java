package io.github.authservice.crowdfund.feature.reward.user.dto.fetch;

import java.util.List;

public record UserRewardsFetchResponse(
        List<RewardFetchInfo> rewards
) {
}