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

    /**
     * 동일한 이름을 가진 활성화된 카테고리가 존재하는지 확인합니다.
     *
     * @param name 카테고리 이름
     * @return 존재 여부
     */
    boolean existsByNameAndIsActiveTrue(String name);

    /**
     * 동일한 부모와 정렬 순서를 가진 활성화된 카테고리가 존재하는지 확인합니다.
     *
     * @param parentId  부모 ID
     * @param sortOrder 정렬 순서
     * @return 존재 여부
     */
    boolean existsByParentIdAndSortOrderAndIsActiveTrue(Integer parentId, Integer sortOrder);
}
