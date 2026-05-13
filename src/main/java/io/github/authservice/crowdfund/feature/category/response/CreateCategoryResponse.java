package io.github.authservice.crowdfund.feature.category.response;


import java.util.List;

public record CreateCategoryResponse(

        String message,
        List<CategoryInfo> category
)
{
    public record CategoryInfo(
            Long id,
            String name,
            Long parentId,
            Integer sortOrder,
            Integer level,
            boolean isActive
    ) {}
}



