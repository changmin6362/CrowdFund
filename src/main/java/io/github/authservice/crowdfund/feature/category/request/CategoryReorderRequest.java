package io.github.authservice.crowdfund.feature.category.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CategoryReorderRequest(
        @NotEmpty(message = "변경할 순서 리스트가 비어있습니다.")
        List<CategoryOrderUpdate> orders
) {
    // 순서 변경에 필요한 정보만 담는 중첩 레코드
    public record CategoryOrderUpdate(
            @NotNull Long id,
            @NotNull Integer sortOrder
    ) {}
}
