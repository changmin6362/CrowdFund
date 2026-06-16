package io.github.crowdfund.feature.pledge.creator;

import io.github.crowdfund.feature.pledge.creator.dto.fulfill.CreatorPledgeFulfillRequest;
import io.github.crowdfund.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/creator/pledges")
@RequiredArgsConstructor
@Validated
public class CreatorPledgeController {

    private final CreatorPledgeService service;

    /**
     * 보상 이행 상태 변경
     */
    @PostMapping("/{pledgeId}/fulfill")
    public String fulfill(
            @AuthenticationPrincipal SecurityUser securityUser,
            @PathVariable Long pledgeId,
            @Valid @ModelAttribute("fulfillRequest") CreatorPledgeFulfillRequest request) {
        service.fulfill(securityUser, pledgeId, request);
        return "redirect:/creator/pledges/" + pledgeId; // 상세 페이지가 있다면 거기로, 아니면 목록으로
    }
}
