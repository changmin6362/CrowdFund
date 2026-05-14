package io.github.authservice.crowdfund.domain.category;


import io.github.authservice.crowdfund.feature.category.model.Category;
import jakarta.validation.constraints.NotBlank;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface CategoryMapper {
    // 카테고리 저장 (생성 후 ID를 반환받음)
    void insert(Category category);

    // 전체 활성 카테고리 목록 조회
    List<Category> findAllActive();

    // ID로 단건 조회
    Optional<Category> findById(Long id);

    // 이름 수정
    void updateName(@Param("id") Long id, @Param("name") String name);

    // 순서 수정
    void updateSortOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);

    // 삭제 (is_active를 0으로 변경)
    void delete(Long id);

    void updateSortOrder(@NotBlank(message = "주문 ID는 필수 값입니다.") Long aLong);
}