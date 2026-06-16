package io.github.crowdfund.feature.category.admin;

import io.github.crowdfund.feature.category.admin.dto.active.AdminCategoryActiveRequest;
import io.github.crowdfund.feature.category.admin.dto.create.AdminCategoryCreateRequest;
import io.github.crowdfund.feature.category.admin.dto.move.AdminCategoryMoveRequest;
import io.github.crowdfund.feature.category.admin.dto.rename.AdminCategoryRenameRequest;
import io.github.crowdfund.feature.category.admin.dto.reorder.AdminCategoryReorderRequest;
import io.github.crowdfund.feature.category.user.UserCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/categories")
@RequiredArgsConstructor
@Validated
public class AdminCategoryController {

    private final AdminCategoryService service;
    private final UserCategoryService userCategoryService;

    /**
     * 카테고리 관리 페이지 이동
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("categoryTree", userCategoryService.fetch().categoryTree());
        model.addAttribute("createRequest", new AdminCategoryCreateRequest(null, null));
        return "admin/category/list";
    }

    /**
     * 카테고리 생성
     */
    @PostMapping
    public String create(@Valid @ModelAttribute("createRequest") AdminCategoryCreateRequest request,
                         BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryTree", userCategoryService.fetch().categoryTree());
            return "admin/category/list";
        }
        service.create(request);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 이름 변경
     */
    @PostMapping("/{categoryId}/rename")
    public String rename(@PathVariable Integer categoryId, @Valid AdminCategoryRenameRequest request) {
        service.rename(categoryId, request);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 부모 변경
     */
    @PostMapping("/{categoryId}/parent")
    public String move(@PathVariable Integer categoryId, @Valid AdminCategoryMoveRequest request) {
        service.move(categoryId, request);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 정렬 순서 변경
     */
    @PostMapping("/sort-order")
    public String reorder(@Valid AdminCategoryReorderRequest request) {
        service.reorder(request);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 활성 여부 변경
     */
    @PostMapping("/{categoryId}/toggle")
    public String toggle(@PathVariable Integer categoryId, @Valid AdminCategoryActiveRequest request) {
        service.toggle(categoryId, request);
        return "redirect:/admin/categories";
    }

    /**
     * 카테고리 삭제
     */
    @PostMapping("/{categoryId}/delete")
    public String delete(@PathVariable Integer categoryId) {
        service.delete(categoryId);
        return "redirect:/admin/categories";
    }
}
