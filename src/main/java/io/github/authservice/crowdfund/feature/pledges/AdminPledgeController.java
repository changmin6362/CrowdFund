package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.feature.pledges.response.GetAdminPledgeDetailResponse;
import io.github.authservice.crowdfund.feature.pledges.response.GetAllPledgesResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
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
    public ApiResult<GetAllPledgesResponse> getAllPledges() {
        return ApiResult.success("전체 후원 목록 조회에 성공했습니다.", service.getAllPledges());
    }

    /**
     * 관리자용 후원 상세 조회
     *
     * @param pledgeId 후원 ID
     * @return message, adminPledgeDetail
     */
    @GetMapping("/pledge/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetAdminPledgeDetailResponse> getAdminPledgeDetail(@PathVariable Long pledgeId) {
        return ApiResult.success("관리자용 후원 상세 조회에 성공했습니다.", service.getAdminPledgeDetail(pledgeId));
    }
}
