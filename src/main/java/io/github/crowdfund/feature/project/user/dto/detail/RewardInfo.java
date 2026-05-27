package io.github.crowdfund.feature.project.user.dto.detail;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Alias("UserProjectRewardInfo")
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