package io.github.crowdfund.feature.reward.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record UserRewardsFetchResponse(
        @Schema(description = "리워드 목록")
        List<RewardFetchInfo> rewards
) {
}