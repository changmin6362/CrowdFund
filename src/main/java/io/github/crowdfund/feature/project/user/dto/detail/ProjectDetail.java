package io.github.crowdfund.feature.project.user.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Alias("ProjectDetail")
public record ProjectDetail(
        @Schema(description = "프로젝트 ID")
        Long projectId,

        @Schema(description = "카테고리 이름")
        String categoryName,

        @Schema(description = "프로젝트 생성자 닉네임")
        String creatorNickname,

        @Schema(description = "프로젝트 제목")
        String title,

        @Schema(description = "프로젝트 내용")
        String contentBlocks,

        @Schema(description = "목표 금액")
        BigDecimal goalAmount,

        @Schema(description = "현재 금액")
        BigDecimal currentAmount,

        @Schema(description = "마감 일시")
        LocalDateTime endAt,

        @Schema(description = "프로젝트 상태")
        String status,

        @Schema(description = "리워드 정보 목록")
        List<RewardInfo> rewards
) {
}
