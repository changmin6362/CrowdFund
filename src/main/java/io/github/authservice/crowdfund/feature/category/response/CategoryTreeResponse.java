package io.github.authservice.crowdfund.feature.category.response;

import java.util.List;

public record CategoryTreeResponse(
        Long id,
        String name,
        Integer level,
        Integer sortOrder,
        List<CategoryTreeResponse> children // 자식 카테고리들을 담는 리스트
) {
}
