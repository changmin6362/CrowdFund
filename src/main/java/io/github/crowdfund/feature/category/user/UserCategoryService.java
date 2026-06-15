package io.github.crowdfund.feature.category.user;

import io.github.crowdfund.domain.category.Category;
import io.github.crowdfund.domain.category.CategoryRepository;
import io.github.crowdfund.feature.category.user.dto.fetch.CategoryTreeNode;
import io.github.crowdfund.feature.category.user.dto.fetch.UserFetchCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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

        Map<Integer, CategoryTreeNode> nodeMap = allCategories.stream()
                .collect(Collectors.toMap(
                        Category::id,
                        c -> new CategoryTreeNode(c.id(), c.name(), c.depth(), c.sortOrder(), new ArrayList<>())
                ));

        List<CategoryTreeNode> rootNodes = allCategories.stream()
                .filter(c -> c.parentId() == null)
                .sorted(Comparator.comparingInt(Category::sortOrder))
                .map(c -> nodeMap.get(c.id()))
                .toList();

        for (Category category : allCategories) {
            CategoryTreeNode node = nodeMap.get(category.id());
            if (category.parentId() != null) {
                CategoryTreeNode parentNode = nodeMap.get(category.parentId());
                if (parentNode != null) {
                    parentNode.children().add(node);
                }
            }
        }

        nodeMap.values().forEach(node -> 
                node.children().sort(Comparator.comparingInt(CategoryTreeNode::sortOrder))
        );

        return new UserFetchCategoryResponse(rootNodes);
    }

    /**
     * 특정 카테고리를 포함한 모든 하위 카테고리 ID 목록을 조회합니다.
     *
     * @param categoryId 기준 카테고리 ID
     * @return 기준 카테고리 ID를 포함한 모든 하위 카테고리 ID 목록
     */
    public List<Integer> getAllChildCategoryIds(Integer categoryId) {
        List<Category> allCategories = repository.findByIsActiveTrue();
        
        // 부모 ID별 자식 목록 매핑
        Map<Integer, List<Integer>> parentToChildren = allCategories.stream()
                .filter(c -> c.parentId() != null)
                .collect(Collectors.groupingBy(
                        Category::parentId,
                        Collectors.mapping(Category::id, Collectors.toList())
                ));

        List<Integer> resultIds = new ArrayList<>();
        collectIdsRecursive(categoryId, parentToChildren, resultIds);
        return resultIds;
    }

    private void collectIdsRecursive(Integer currentId, Map<Integer, List<Integer>> parentToChildren, List<Integer> resultIds) {
        resultIds.add(currentId);
        List<Integer> children = parentToChildren.get(currentId);
        if (children != null) {
            for (Integer childId : children) {
                collectIdsRecursive(childId, parentToChildren, resultIds);
            }
        }
    }
}