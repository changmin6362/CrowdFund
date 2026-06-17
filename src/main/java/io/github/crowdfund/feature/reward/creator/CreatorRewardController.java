package io.github.crowdfund.feature.reward.creator;

import io.github.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateRequest;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateRequest;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateStockRequest;
import io.github.crowdfund.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creator/projects/{projectId}/rewards")
@RequiredArgsConstructor
@Validated
public class CreatorRewardController {

    private final CreatorRewardService service;
    private final io.github.crowdfund.feature.reward.user.UserRewardService userRewardService;

    /**
     * 리워드 관리 페이지
     */
    @GetMapping
    public String manage(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long projectId,
            Model model) {
        model.addAttribute("rewards", userRewardService.fetch(projectId).rewards());
        model.addAttribute("projectId", projectId);
        model.addAttribute("createRequest", new CreatorRewardCreateRequest("", "", java.math.BigDecimal.valueOf(1000), 1));
        return "reward/creator/manage";
    }

    /**
     * 프로젝트에 리워드 등록
     */
    @PostMapping
    public String create(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long projectId,
            @Valid @ModelAttribute("createRequest") CreatorRewardCreateRequest request) {
        service.create(securityUser, projectId, request);
        return "redirect:/creator/projects/" + projectId + "/rewards";
    }

    /**
     * 리워드 정보 수정 페이지
     */
    @GetMapping("/{rewardId}/edit")
    public String editForm(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long projectId,
            @PathVariable Long rewardId,
            Model model) {
        // 실제 운영 시에는 서비스에서 rewardId로 정보를 조회해야 함.
        model.addAttribute("projectId", projectId);
        model.addAttribute("rewardId", rewardId);
        model.addAttribute("updateRequest", new CreatorRewardUpdateRequest("", "", java.math.BigDecimal.ZERO));
        model.addAttribute("stockRequest", new CreatorRewardUpdateStockRequest(1));
        return "reward/creator/edit";
    }

    /**
     * 리워드 정보 수정
     */
    @PostMapping("/{rewardId}/update")
    public String update(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long projectId,
            @PathVariable Long rewardId,
            @Valid @ModelAttribute("updateRequest") CreatorRewardUpdateRequest request) {
        service.update(securityUser, rewardId, request);
        return "redirect:/creator/projects/" + projectId + "/rewards";
    }

    /**
     * 리워드 재고 수정
     */
    @PostMapping("/{rewardId}/stock")
    public String updateStock(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long projectId,
            @PathVariable Long rewardId,
            @Valid @ModelAttribute("stockRequest") CreatorRewardUpdateStockRequest request) {
        service.updateStock(securityUser, rewardId, request);
        return "redirect:/creator/projects/" + projectId + "/rewards";
    }

    /**
     * 리워드 삭제
     */
    @PostMapping("/{rewardId}/delete")
    public String delete(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long projectId,
            @PathVariable Long rewardId) {
        service.delete(securityUser, rewardId);
        return "redirect:/creator/projects/" + projectId + "/rewards";
    }
}

