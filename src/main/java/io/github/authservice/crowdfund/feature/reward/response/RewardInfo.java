package io.github.authservice.crowdfund.feature.reward.response;

import org.apache.ibatis.type.Alias;

@Alias("RewardInfo")
public record RewardInfo(
        Long rewardId,
        String title,
        String description,
        Integer price,
        Integer stock

) {
}