package io.github.crowdfund.feature.reward.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Alias("RewardFetchInfo")
public record RewardFetchInfo(
        @Schema(description = "리워드 ID")
        Long rewardId,

        @Schema(description = "프로젝트 ID")
        Long projectId,

        @Schema(description = "리워드 제목")
        String title,

        @Schema(description = "리워드 설명")
        String description,

        @Schema(description = "리워드 가격")
        BigDecimal price,

        @Schema(description = "리워드 재고")
        Integer stock,

        @Schema(description = "리워드 생성일시")
        LocalDateTime createdAt
) {
}