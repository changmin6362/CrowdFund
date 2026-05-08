package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.feature.pledges.response.PledgeDeleteResponse;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pledges")
@RequiredArgsConstructor
public class PledgeController {

    private final PledgeService pledgeService;

    /**
     * 특정 펀딩 상세 조회
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return 펀딩상세정보
     */
    @GetMapping("/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public PledgeDetailResponse getPledgeDetail(@PathVariable Long pledgeId) {
        return pledgeService.getPledgeDetail(pledgeId);
    }

    /**
     * 펀딩 취소
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return 완료 메세지
     */
    @DeleteMapping("/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public PledgeDeleteResponse deletePledge(@PathVariable Long pledgeId) {
        return pledgeService.deletePledge(pledgeId);
    }
}
