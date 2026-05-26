package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.feature.pledges.response.GetAdminPledgeDetailResponse;
import io.github.authservice.crowdfund.feature.pledges.response.GetAllPledgesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminPledgeController {

    private final PledgeService service;

    /**
     * 전체 후원 목록 조회
     *
     * @return message, pledges
     */
    @GetMapping("/pledge")
    @ResponseStatus(HttpStatus.OK)
    public GetAllPledgesResponse getAllPledges() {
        return service.getAllPledges();
    }

    /**
     * 관리자용 후원 상세 조회
     *
     * @param pledgeId 후원 ID
     * @return message, adminPledgeDetail
     */
    @GetMapping("/pledge/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public GetAdminPledgeDetailResponse getAdminPledgeDetail(@PathVariable Long pledgeId) {
        return service.getAdminPledgeDetail(pledgeId);
    }
}
