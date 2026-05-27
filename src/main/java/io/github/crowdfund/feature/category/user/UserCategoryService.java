package io.github.crowdfund.feature.category.user;

import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.feature.category.user.dto.fetch.CategoryNode;
import io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserCategoryService {

    private final CategoryRepository repository;

    /**
     * 카테고리 트리 조회 도메인 로직
     */
    @Transactional
    public UserFetchCategoryResponse fetch() {
        List<Category> allCategories = repository.findByIsActiveTrue();

        Map<Integer, CategoryNode> nodeMap = allCategories.stream()
                .collect(Collectors.toMap(
                        Category::id,
                        c -> new CategoryNode(c.id(), c.name(), c.depth(), c.sortOrder(), new ArrayList<>())
                ));

        List<CategoryNode> rootNodes = allCategories.stream()
                .filter(c -> c.parentId() == null)
                .map(c -> nodeMap.get(c.id()))
                .toList();

        for (Category category : allCategories) {
            CategoryNode node = nodeMap.get(category.id());
            if (category.parentId() != null) {
                CategoryNode parentNode = nodeMap.get(category.parentId());
                if (parentNode != null) {
                    parentNode.children().add(node);
                }
            }
        }

        return new UserFetchCategoryResponse(rootNodes);
    }
}