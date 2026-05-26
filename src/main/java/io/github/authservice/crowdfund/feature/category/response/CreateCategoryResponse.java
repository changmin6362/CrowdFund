package io.github.authservice.crowdfund.feature.category.response;


import io.github.authservice.crowdfund.domain.category.Category;

import java.util.List;

public record CreateCategoryResponse(
        Category category
) {
}