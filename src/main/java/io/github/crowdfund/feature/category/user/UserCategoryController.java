package io.github.crowdfund.feature.category.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
@Validated
public class UserCategoryController {

    private final UserCategoryService service;

    /**
     * 카테고리 트리 조회 페이지 (Thymeleaf)
     */
    @GetMapping
    public String fetch(Model model) {
        model.addAttribute("categories", service.fetch().categoryTree());
        return "category/list";
    }
}
