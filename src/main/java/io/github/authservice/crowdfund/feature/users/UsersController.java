package io.github.authservice.crowdfund.feature.users;

import io.github.authservice.crowdfund.feature.pledges.PledgeService;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UsersController {

    private final PledgeService pledgeService;

    /**
     * 내가 참여한 펀딩 조회
     * @return 메세지, 펀딩 정보
     */
    @GetMapping("/pledges")
    @ResponseStatus(HttpStatus.OK)
    public PledgeListResponse getAllPledges() {
        return pledgeService.getAllPledges();
    }
}
