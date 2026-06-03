package io.github.crowdfund.feature.project.creator.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatorProjectCreateRequest(
        @Schema(description = "프로젝트 카테고리 ID", example = "1")
        @NotNull(message = "카테고리 선택은 필수입니다.")
        Integer categoryId,

        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        @NotBlank(message = "프로젝트 제목은 필수입니다.")
        String title,

        @Schema(
                description = "프로젝트 본문 콘텐츠 블록 데이터 (JSON)",
                example = """
                {
                  "time": 1717200000000,
                  "blocks": [
                    {
                      "id": "b1",
                      "type": "header",
                      "data": { "text": "프로젝트 소개", "level": 2 }
                    },
                    {
                      "id": "b2",
                      "type": "paragraph",
                      "data": { "text": "친환경 소재로 만든 방수 미니멀 백팩입니다. 일상과 여행 모두에 완벽하게 어울립니다." }
                    },
                    {
                      "id": "b3",
                      "type": "image",
                      "data": { "url": "https://crowdfund.com", "caption": "백팩 착용 정면 사진" }
                    }
                  ],
                  "version": "2.28.2"
                }
                """
        )
        @NotNull(message = "콘텐트 블록 데이터는 필수입니다.")
        Object contentBlocks,

        @Schema(description = "목표 금액", example = "1000000")
        @NotNull(message = "목표 금액은 필수입니다.")
        @Positive(message = "목표 금액은 0보다 커야 합니다.")
        BigDecimal goalAmount,

        @Schema(description = "프로젝트 종료일", example = "2024-01-01T00:00:00")
        @NotNull(message = "프로젝트 종료일은 필수입니다.")
        LocalDateTime endAt

) {
}