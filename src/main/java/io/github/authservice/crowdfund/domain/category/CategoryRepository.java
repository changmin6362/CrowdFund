package io.github.authservice.crowdfund.domain.category;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends ListCrudRepository<Category, Long> {
    List<Category> findByParentId(Long parentId);
}
