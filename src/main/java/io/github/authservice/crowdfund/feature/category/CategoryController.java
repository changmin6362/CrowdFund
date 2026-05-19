package io.github.authservice.crowdfund.feature.category;

import io.github.authservice.crowdfund.feature.category.response.GetCategoryTreeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    /**
     * 카테고리 트리 조회
     *
     * @return message, categoryTree
     */
    @GetMapping("/tree")
    @ResponseStatus(HttpStatus.OK)
    public GetCategoryTreeResponse getCategoryTree() {
        return service.getCategoryTree();
    }
}
