package io.github.crowdfund.feature.project.creator.dto.create;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatorProjectCreateRequest(
        @Schema(description = "프로젝트 카테고리 ID")
        @NotNull(message = "카테고리 선택은 필수입니다.")
        Integer categoryId,

        @Schema(description = "프로젝트 제목")
        @NotBlank(message = "프로젝트 제목은 필수입니다.")
        String title,

        @Schema(description = "프로젝트 콘텐트 블럭 데이터")
        @NotBlank(message = "콘텐트 블록 데이터는 필수입니다.")
        String contentBlocks,

        @Schema(description = "목표 금액")
        @NotNull(message = "목표 금액은 필수입니다.")
        @Positive(message = "목표 금액은 0보다 커야 합니다.")
        BigDecimal goalAmount,

        @Schema(description = "프로젝트 종료일")
        @NotNull(message = "프로젝트 종료일은 필수입니다.")
        LocalDateTime endAt

) {
}