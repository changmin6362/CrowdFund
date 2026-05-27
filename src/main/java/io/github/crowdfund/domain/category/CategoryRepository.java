package io.github.crowdfund.domain.category;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 카테고리 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface CategoryRepository extends ListCrudRepository<Category, Integer> {

    /**
     * 활성화 상태인 모든 카테고리를 조회합니다.
     *
     * @return 활성화된 카테고리 목록
     */
    List<Category> findByIsActiveTrue();

    /**
     * 부모 ID가 일치하고 활성화 상태인 카테고리를 조회합니다.
     *
     * @param parentId 부모 ID
     * @return 카테고리 목록
     */
    List<Category> findByParentIdAndIsActiveTrueOrderBySortOrderAsc(Integer parentId);
}
