package io.github.authservice.crowdfund.feature.category.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryCreateRequest(
        @NotBlank(message = "카테고리 이름은 필수입니다.")
        String name,

        Long parentId, // 최상위 카테고리일 경우 null 가능

        @NotNull(message = "순서는 필수입니다.")
        Integer sortOrder,

        @NotNull(message = "레벨은 필수입니다.")
        Integer level
) {
}
