package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.feature.pledges.response.GetAllPledgesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
