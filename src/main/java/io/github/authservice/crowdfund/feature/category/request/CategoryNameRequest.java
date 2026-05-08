package io.github.authservice.crowdfund.feature.category.request;

import jakarta.validation.constraints.NotBlank;

public record CategoryNameRequest(
        @NotBlank(message = "변경할 카테고리 이름은 필수입니다.")
        String name
) {
}
