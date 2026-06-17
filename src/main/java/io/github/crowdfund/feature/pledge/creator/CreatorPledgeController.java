package io.github.crowdfund.feature.pledge.creator;

import io.github.crowdfund.feature.pledge.creator.dto.fulfill.CreatorPledgeFulfillRequest;
import io.github.crowdfund.feature.project.creator.dto.extract.ShippingInfo;
import io.github.crowdfund.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/creator/pledges")
@RequiredArgsConstructor
@Validated
public class CreatorPledgeController {

    private final CreatorPledgeService service;

    /**
     * 창작자용 후원 상세 조회
     */
    @GetMapping("/{pledgeId}")
    public String detail(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId,
            Model model) {
        ShippingInfo pledge = service.getDetailForCreator(securityUser, pledgeId);
        model.addAttribute("pledge", pledge);
        model.addAttribute("fulfillRequest", new CreatorPledgeFulfillRequest(null));
        return "project/creator/pledge-detail";
    }

    /**
     * 보상 이행 상태 변경
     */
    @PostMapping("/{pledgeId}/fulfill")
    public String fulfill(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId,
            @Valid @ModelAttribute("fulfillRequest") CreatorPledgeFulfillRequest request) {
        service.fulfill(securityUser, pledgeId, request);
        return "redirect:/creator/pledges/" + pledgeId;
    }
}
