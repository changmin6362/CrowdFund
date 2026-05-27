package io.github.authservice.crowdfund.feature.category.admin.dto.create;


import io.github.authservice.crowdfund.domain.category.Category;

public record AdminCategoryCreateResponse(
        Category category
) {
}