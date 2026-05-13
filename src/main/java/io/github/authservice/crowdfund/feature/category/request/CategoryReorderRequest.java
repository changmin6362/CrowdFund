package io.github.authservice.crowdfund.feature.category.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

// 순서 변경 요청
public record CategoryReorderRequest(
        List<CategoryOrderUpdate> orders
) {
    public record CategoryOrderUpdate(
            @NotBlank(message = "주문 ID는 필수 값입니다.")
            Long id,  // 카테고리 id
            Integer sortOrder // 정렬 순서

    ) {}
}
