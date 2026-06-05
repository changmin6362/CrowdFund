package io.github.crowdfund.feature.project.user.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Alias("UserProjectRewardInfo")
public record RewardInfo(
        @Schema(description = "보상 ID", example = "1")
        Long rewardId,

        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "보상 제목", example = "예시 보상")
        String title,

        @Schema(description = "보상 설명", example = "예시 설명")
        String description,

        @Schema(description = "가격", example = "10000")
        BigDecimal price,

        @Schema(description = "재고", example = "10")
        Integer stock,

        @Schema(description = "생성 일시", example = "2023-08-01T12:00:00")
        LocalDateTime createdAt

) {
}