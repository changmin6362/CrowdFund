package io.github.crowdfund.feature.pledges.admin;

import io.github.crowdfund.feature.pledges.admin.dto.detail.AdminPledgeDetailResponse;
import io.github.crowdfund.feature.pledges.admin.dto.fetch.AdminPledgesFetchResponse;
import io.github.crowdfund.global.common.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminPledgeController {

    private final AdminPledgeService service;

    /**
     * 관리자용 전체 후원 목록 조회
     *
     * @return message, pledges
     */
    @GetMapping("/pledge")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<AdminPledgesFetchResponse> fetch() {
        return ApiResult.success("전체 후원 목록 조회에 성공했습니다.", service.fetch());
    }

    /**
     * 관리자용 후원 상세 조회
     *
     * @param pledgeId 후원 ID
     * @return message, adminPledgeDetail
     */
    @GetMapping("/pledge/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<AdminPledgeDetailResponse> detail(@PathVariable Long pledgeId) {
        return ApiResult.success("관리자용 후원 상세 조회에 성공했습니다.", service.detail(pledgeId));
    }
}
