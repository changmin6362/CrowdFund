package io.github.authservice.crowdfund.domain.category;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 카테고리 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface CategoryRepository extends ListCrudRepository<Category, Integer> {
    /**
     * 부모 카테고리 ID로 하위 카테고리 목록을 조회합니다.
     *
     * @param parentId 부모 카테고리 ID
     * @return 하위 카테고리 목록
     */
    List<Category> findByParentId(Integer parentId);

    /**
     * 활성화 상태인 모든 카테고리를 조회합니다.
     *
     * @return 활성화된 카테고리 목록
     */
    List<Category> findByIsActiveTrue();
}
