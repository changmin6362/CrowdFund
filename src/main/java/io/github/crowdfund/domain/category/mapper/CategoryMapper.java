package io.github.crowdfund.domain.category.mapper;

import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CategoryMapper {
    void insert(@Param("category") AdminCategoryCreateRequest request,
                @Param("depth") Integer depth,
                @Param("sortOrder") Integer sortOrder);

    void updateName(@Param("id") Long id, @Param("name") String name);

    void updateSortOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);

    void delete(Long id);

    void updateParentId(@Param("id") Integer id, @Param("parentId") Integer parentId, @Param("depth") Integer depth);
}