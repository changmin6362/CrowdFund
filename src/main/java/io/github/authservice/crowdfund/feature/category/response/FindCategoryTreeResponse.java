package io.github.authservice.crowdfund.feature.category.response;

import java.util.List;

public record FindCategoryTreeResponse(
        String message,
        List<CategoryTreeResponse> categories

) {}
