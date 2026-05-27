package io.github.crowdfund.feature.project.user.dto.detail;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Alias("ProjectDetail")
public record ProjectDetail(
        Long projectId,
        String categoryName,
        String creatorNickname,
        String title,
        String contentBlocks,
        BigDecimal goalAmount,
        BigDecimal currentAmount,
        LocalDateTime endAt,
        String status,
        List<RewardInfo> rewards
) {
}
