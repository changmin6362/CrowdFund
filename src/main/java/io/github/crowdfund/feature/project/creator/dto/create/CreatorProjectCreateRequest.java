package io.github.crowdfund.feature.project.creator.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record CreatorProjectCreateRequest(
        @NotNull(message = "카테고리 선택은 필수입니다.")
        Integer categoryId,

        @NotBlank(message = "프로젝트 제목은 필수입니다.")
        String title,

        @NotBlank(message = "콘텐트 블록 데이터는 필수입니다.")
        String contentBlocks,

        @NotNull(message = "목표 금액은 필수입니다.")
        @Positive(message = "목표 금액은 0보다 커야 합니다.")
        BigDecimal goalAmount,

        @NotNull(message = "프로젝트 종료일은 필수입니다.")
        LocalDateTime endAt

) {
}