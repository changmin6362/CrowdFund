package io.github.crowdfund.feature.reward.creator.dto.create;

import io.github.crowdfund.feature.reward.creator.dto.RewardInfo;
import io.swagger.v3.oas.annotations.media.Schema;

public record CreatorRewardCreateResponse(
        @Schema(description = "생성된 리워드 정보")
        RewardInfo createdReward
) {
}