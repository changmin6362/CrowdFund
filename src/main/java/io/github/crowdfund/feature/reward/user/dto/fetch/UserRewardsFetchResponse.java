package io.github.crowdfund.feature.reward.user.dto.fetch;

import java.util.List;

public record UserRewardsFetchResponse(
        List<RewardFetchInfo> rewards
) {
}