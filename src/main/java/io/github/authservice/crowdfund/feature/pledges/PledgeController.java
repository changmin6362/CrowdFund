package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.feature.pledges.request.CreatePledgeRequest;
import io.github.authservice.crowdfund.feature.pledges.response.DeletePledgeResponse;
import io.github.authservice.crowdfund.feature.pledges.response.GetPledgeDetailResponse;
import io.github.authservice.crowdfund.feature.pledges.response.CreatePledgeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PledgeController {

    private final PledgeService service;

    /**
     * 프로젝트 후원 참여
     *
     * @param userId  유저 아이디
     * @param request 후원 정보
     * @return 메세지
     */
    @PostMapping("/project/pledges/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreatePledgeResponse createPledge(
            @PathVariable Long userId,
            @Valid @RequestBody CreatePledgeRequest request) {
        return service.createPledge(userId, request);
    }

    /**
     * 후원 상세 조회
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return message, pledgeDetail
     */
    @GetMapping("/pledges/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public GetPledgeDetailResponse getPledgeDetail(@PathVariable Long pledgeId) {
        return service.getPledgeDetail(pledgeId);
    }

    /**
     * 후원 취소
     *
     * @param pledgeId 해당 후원 아이디
     * @return message
     */
    @DeleteMapping("/pledges/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public DeletePledgeResponse deletePledge(@PathVariable Long pledgeId) {
        return service.deletePledge(pledgeId);
    }


}
