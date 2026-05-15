package io.github.authservice.crowdfund.feature.project.response;

import io.github.authservice.crowdfund.feature.reward.response.RewardInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProjectDetailInfo(
        Long projectId,
        String categoryName,
        String creatorNickname,
        String title,
        String contentBlocks,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        LocalDateTime endAt,
        String status,
        List<RewardInfo> rewardList
) {}
