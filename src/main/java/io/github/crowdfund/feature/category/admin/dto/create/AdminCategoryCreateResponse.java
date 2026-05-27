package io.github.crowdfund.feature.category.admin.dto.create;


import io.github.crowdfund.domain.category.Category;

public record AdminCategoryCreateResponse(
        Category category
) {
}