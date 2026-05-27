package io.github.authservice.crowdfund.feature.reward.user.dto.fetch;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Alias("RewardInfo")
public record RewardFetchInfo(
        Long rewardId,
        Long projectId,
        String title,
        String description,
        BigDecimal price,
        Integer stock,
        LocalDateTime createdAt

) {
}