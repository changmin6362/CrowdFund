package io.github.crowdfund.feature.project.user.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Alias("ProjectDetail")
public record ProjectDetail(
        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "카테고리 이름", example = "디자인")
        String categoryName,

        @Schema(description = "프로젝트 생성자 닉네임", example = "김공자")
        String creatorNickname,

        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        String title,

        @Schema(description = "프로젝트 내용", example = "프로젝트 내용 예시")
        String contentBlocks,

        @Schema(description = "목표 금액", example = "1000000")
        BigDecimal goalAmount,

        @Schema(description = "현재 금액", example = "500000")
        BigDecimal currentAmount,

        @Schema(description = "마감 일시", example = "2023-08-01T12:00:00")
        LocalDateTime endAt,

        @Schema(description = "프로젝트 상태")
        String status,

        @Schema(description = "리워드 정보 목록")
        List<RewardInfo> rewards
) {
}
