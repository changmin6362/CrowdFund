package io.github.crowdfund.feature.pledge.my;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.github.crowdfund.feature.pledge.my.dto.create.MyPledgeCreateRequest;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.github.crowdfund.global.security.SecurityUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pledges/me")
@RequiredArgsConstructor
@Validated
public class MyPledgeController {

    private final MyPledgeService service;

    /**
     * 프로젝트 후원하기
     */
    @PostMapping
    public String create(
            @AuthenticationPrincipal SecurityUser securityUser,
            @Valid @ModelAttribute("pledgeRequest") MyPledgeCreateRequest request,
            Model model) {
        service.create(securityUser.getUserId(), request);
        return "redirect:/pledges/me";
    }

    /**
     * 내 후원 상세 조회
     */
    @GetMapping("/{pledgeId}")
    public String detail(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId,
            Model model) {
        model.addAttribute("pledge", service.detail(securityUser, pledgeId).myPledgeDetail());
        return "pledge/my/detail";
    }

    /**
     * 후원 취소
     */
    @PostMapping("/{pledgeId}/cancel")
    public String cancel(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId) {
        service.cancel(securityUser, pledgeId);
        return "redirect:/pledges/me";
    }

    /**
     * 내가 후원한 프로젝트 목록 조회 (복합 커서 기반 최신순 페이지네이션)
     */
    @GetMapping
    public String fetch(
            @AuthenticationPrincipal SecurityUser securityUser,
            @RequestParam(required = false) FulfillmentStatus fulfillmentStatus,
            @RequestParam(required = false) PledgeStatus pledgeStatus,
            @ModelAttribute CursorRequest cursorRequest,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            Model model
    ) {
        model.addAttribute("pledgeData", service.fetch(securityUser.getUserId(), fulfillmentStatus, pledgeStatus, cursorRequest, limit));
        model.addAttribute("fulfillmentStatus", fulfillmentStatus);
        model.addAttribute("pledgeStatus", pledgeStatus);
        return "pledge/my/list";
    }
}
