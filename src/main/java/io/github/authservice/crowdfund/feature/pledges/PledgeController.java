package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.feature.pledges.request.PledgeRequest;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeDeleteResponse;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeDetailResponse;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PledgeController {

    private final PledgeService pledgeService;

    /**
     * 특정 후원 상세 조회
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return 펀딩상세정보
     */
    @GetMapping("/pledges/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public PledgeDetailResponse getPledgeDetail(@PathVariable Long pledgeId) {
        return pledgeService.getPledgeDetail(pledgeId);
    }

    /**
     * 후원 취소
     *
     * @param pledgeId 해당 후원 아이디
     * @return 완료 메세지
     */
    @DeleteMapping("/pledges/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public PledgeDeleteResponse deletePledge(@PathVariable Long pledgeId) {
        return pledgeService.deletePledge(pledgeId);
    }

    /**
     * 프로젝트 후원 참여
     *
     * @param request 펀딩 정보
     * @return 메세지
     */
    @PostMapping("/project/pledges")
    @ResponseStatus(HttpStatus.CREATED)
    public PledgeResponse createPledge(@Valid @RequestBody PledgeRequest request) {
        return pledgeService.createPledge(request);
    }
}
