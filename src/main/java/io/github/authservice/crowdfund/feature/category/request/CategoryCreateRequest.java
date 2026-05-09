package io.github.authservice.crowdfund.feature.category.request;


// 생성 요청
public record CategoryCreateRequest(
        String name,
        Long parentId,
        Integer sortOrder,
        Integer level
) {}

