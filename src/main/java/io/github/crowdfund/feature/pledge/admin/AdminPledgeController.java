package io.github.crowdfund.feature.pledge.admin;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/pledges")
@RequiredArgsConstructor
@Validated
public class AdminPledgeController {

    private final AdminPledgeService service;

    /**
     * 관리자용 전체 후원 목록 조회 (복합 커서 기반 최신순 페이지네이션)
     */
    @GetMapping()
    public String fetch(
            @RequestParam(required = false) FulfillmentStatus fulfillmentStatus,
            @RequestParam(required = false) PledgeStatus pledgeStatus,
            @ModelAttribute CursorRequest cursorRequest,
            @RequestParam(defaultValue = "10") @Positive Integer limit,
            Model model
    ) {
        model.addAttribute("pledgeData", service.fetch(fulfillmentStatus, pledgeStatus, cursorRequest, limit));
        model.addAttribute("fulfillmentStatus", fulfillmentStatus);
        model.addAttribute("pledgeStatus", pledgeStatus);
        return "admin/pledge/list";
    }

    /**
     * 관리자용 후원 상세 조회
     */
    @GetMapping("/{pledgeId}")
    public String detail(@PathVariable Long pledgeId, Model model) {
        model.addAttribute("pledge", service.detail(pledgeId).adminPledgeDetail());
        return "admin/pledge/detail";
    }
}
