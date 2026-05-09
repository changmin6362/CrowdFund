package io.github.authservice.crowdfund.feature.category.request;

import java.util.List;

// 순서 변경 요청
public record CategoryReorderRequest(List<CategoryOrderUpdate> orders) {
    public record CategoryOrderUpdate(Long id, Integer sortOrder) {}
}
