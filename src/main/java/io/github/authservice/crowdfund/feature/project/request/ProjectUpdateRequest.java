package io.github.authservice.crowdfund.feature.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 프로젝트 정보 수정 요청 객체.
 * 수정 시 식별을 위한 projectId를 포함하며, 생성과 다른 검증 로직을 가질 수 있음.
 */
public record ProjectUpdateRequest(
        @NotNull(message = "수정할 프로젝트 ID는 필수임")
        Long projectId,

        @NotBlank(message = "프로젝트 제목은 필수임")
        String title,

        @NotBlank(message = "프로젝트 설명은 필수임")
        String description,

        @NotNull(message = "목표 금액은 필수임")
        @Positive(message = "목표 금액은 0보다 커야 함")
        BigDecimal goalAmount,

        @NotNull(message = "카테고리 선택은 필수임")
        Integer categoryId
) {
}