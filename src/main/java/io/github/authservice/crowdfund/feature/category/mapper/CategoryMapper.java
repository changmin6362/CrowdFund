package io.github.authservice.crowdfund.feature.category.mapper;

@Mapper
public interface CategoryMapper     {
    // 1. 카테고리 생성 (생성된 ID를 바로 받아오기 위해 사용)
    void insertCategory(Category category);

    // 2. 전체 카테고리 조회 (일단 평면적으로 다 가져옴)
    List<Category> findAllActiveCategories();

    // 3. 특정 ID로 조회
    Optional<Category> findById(Long id);

    // 4. 이름 수정
    void updateName(@Param("id") Long id, @Param("name") String name);

    // 5. 순서 수정
    void updateSortOrder(@Param("id") Long id, @Param("sortOrder") Integer sortOrder);

    // 6. 삭제 (논리 삭제)
    void delete(Long id);
}
