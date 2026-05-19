package io.github.authservice.crowdfund.domain.category;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface CategoryMapper {
    void insert(Category category);

    void updateName(@Param("id") Long id, @Param("name") String name);

    void updateSortOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);

    void delete(Long id);

    void updateParentId(@Param("id") Integer id, @Param("parentId") Integer parentId, @Param("depth") Integer depth);
}