package io.github.authservice.crowdfund.feature.category.request;

import jakarta.validation.constraints.NotBlank;

// 이름 수정 요청
public record CategoryNameRequest(
        @NotBlank(message = "이름은 필수 입력값입니다.")
        String name
) {}
