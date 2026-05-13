package io.github.authservice.crowdfund.feature.project.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 프로젝트 신규 생성 요청 객체.
 * 생성 시 불필요한 ID 및 서버에서 생성하는 시간 정보는 제외함.
 */
public record ProjectCreateRequest(
        @NotBlank(message = "프로젝트 제목은 필수임")
        String title,

        @NotBlank(message = "프로젝트 설명은 필수임")
        String description,

        @NotNull(message = "목표 금액은 필수임")
        @Positive(message = "목표 금액은 0보다 커야 함")
        BigDecimal goalAmount,

        @NotNull(message = "카테고리 선택은 필수임")
        Integer categoryId,

        @NotNull(message = "창작자 정보는 필수임")
        Long creatorId
) {
}