package io.github.crowdfund.feature.reward.creator.dto;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Alias("RewardInfo")
public record RewardInfo(
        Long rewardId,
        Long projectId,
        String title,
        String description,
        BigDecimal price,
        Integer stock,
        LocalDateTime createdAt

) {
}