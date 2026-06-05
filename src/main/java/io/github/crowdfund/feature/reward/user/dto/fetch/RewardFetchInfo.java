package io.github.crowdfund.feature.reward.user.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Alias("RewardFetchInfo")
public record RewardFetchInfo(
        @Schema(description = "리워드 ID", example = "1")
        Long rewardId,

        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "리워드 제목", example = "리워드 제목 예시")
        String title,

        @Schema(description = "리워드 설명", example = "리워드 설명 예시")
        String description,

        @Schema(description = "리워드 가격", example = "10000")
        BigDecimal price,

        @Schema(description = "리워드 재고", example = "10")
        Integer stock,

        @Schema(description = "리워드 생성일시", example = "2023-08-01T12:00:00")
        LocalDateTime createdAt
) {
}