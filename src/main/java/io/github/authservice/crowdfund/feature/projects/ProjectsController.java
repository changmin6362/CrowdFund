package io.github.authservice.crowdfund.feature.projects;

import io.github.authservice.crowdfund.feature.pledges.PledgeService;
import io.github.authservice.crowdfund.feature.pledges.request.PledgeRequest;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
public class ProjectsController {

    private final PledgeService pledgeService;

    /**
     * 프로젝트 펀딩 참여
     *
     * @param request 펀딩 정보
     * @return 메세지
     */
    @PostMapping("/{projectId}/pledges")
    @ResponseStatus(HttpStatus.CREATED)
    public PledgeResponse createPledge(@Valid @RequestBody PledgeRequest request) {
        return pledgeService.createPledge(request);
    }
}
