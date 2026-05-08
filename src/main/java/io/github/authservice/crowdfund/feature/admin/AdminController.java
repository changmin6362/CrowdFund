package io.github.authservice.crowdfund.feature.admin;

import io.github.authservice.crowdfund.feature.pledges.PledgeService;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final PledgeService pledgeService;

    /**
     * 전체 펀딩 목록 조회
     *
     * @return 메세지와 펀딩 정보 리스트
     */
    @GetMapping("/pledge")
    @ResponseStatus(HttpStatus.OK)
    public PledgeListResponse getAllPledges() {
        return pledgeService.getAllPledges();
    }
}
