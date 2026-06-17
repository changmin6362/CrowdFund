package io.github.crowdfund.feature.project.creator;

import io.github.crowdfund.feature.project.creator.dto.create.CreatorProjectCreateRequest;
import io.github.crowdfund.feature.project.creator.dto.update.CreatorProjectUpdateRequest;
import io.github.crowdfund.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creator/projects")
@RequiredArgsConstructor
@Validated
public class CreatorProjectController {

    private final CreatorProjectService service;

    /**
     * 프로젝트 생성 페이지
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("projectRequest", new CreatorProjectCreateRequest(null, null, null, null, null));
        return "project/creator/create";
    }

    /**
     * 프로젝트 생성
     */
    @PostMapping
    public String create(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @ModelAttribute("projectRequest") CreatorProjectCreateRequest request) {
        service.create(securityUser.getUserId(), request);
        return "redirect:/creator/projects/me";
    }

    /**
     * 프로젝트 제목과 본문 수정 페이지
     */
    @GetMapping("/{projectId}/edit")
    public String updateForm(
            @PathVariable Long projectId,
            @AuthenticationPrincipal SecurityUser securityUser,
            Model model) {
        // 실제로는 현재 정보를 조회해서 넘겨줘야 함. 여기서는 단순화.
        model.addAttribute("projectId", projectId);
        model.addAttribute("updateRequest", new CreatorProjectUpdateRequest(null, null));
        return "project/creator/edit";
    }

    /**
     * 프로젝트 제목과 본문 수정
     */
    @PostMapping("/{projectId}/update")
    public String update(
            @PathVariable Long projectId,
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @ModelAttribute("updateRequest") CreatorProjectUpdateRequest request) {
        service.update(securityUser, projectId, request);
        return "redirect:/creator/projects/me";
    }

    /**
     * 프로젝트 삭제
     */
    @PostMapping("/{projectId}/delete")
    public String delete(
            @PathVariable Long projectId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        service.delete(securityUser, projectId);
        return "redirect:/creator/projects/me";
    }

    /**
     * 내 프로젝트 조회
     */
    @GetMapping("/me")
    public String fetch(@AuthenticationPrincipal SecurityUser securityUser, Model model) {
        try {
            model.addAttribute("projectData", service.fetch(securityUser.getUserId()));
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "project/creator/list";
    }

    /**
     * 후원자들의 배송지 목록 조회
     */
    @GetMapping("/{projectId}/shipping-infos")
    public String extract(
            @PathVariable Long projectId,
            @AuthenticationPrincipal SecurityUser securityUser,
            Model model) {
        try {
            model.addAttribute("shippingData", service.extract(securityUser, projectId));
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "project/creator/shipping";
    }

    /**
     * 프로젝트 취소
     */
    @PostMapping("/{projectId}/cancel")
    public String cancel(
            @PathVariable Long projectId,
            @AuthenticationPrincipal SecurityUser securityUser) {
        service.cancel(securityUser, projectId);
        return "redirect:/creator/projects/me";
    }
}