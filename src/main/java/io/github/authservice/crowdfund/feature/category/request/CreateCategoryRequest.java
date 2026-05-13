package io.github.authservice.crowdfund.feature.category.request;


import jakarta.validation.constraints.NotBlank;

// 생성 요청
public record CreateCategoryRequest(
        @NotBlank (message = "필수입력 란을 채워주세요")
        String name,
        Integer parentId,
        Integer sortOrder,
        Integer level
) {}

