package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.feature.pledges.request.CreatePledgeRequest;
import io.github.authservice.crowdfund.feature.pledges.request.PatchFulfillmentRequest;
import io.github.authservice.crowdfund.feature.pledges.response.CreatePledgeResponse;
import io.github.authservice.crowdfund.feature.pledges.response.GetPledgeDetailResponse;
import io.github.authservice.crowdfund.feature.pledges.response.PatchFulfillmentResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
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
     * @return message, pledgeId
     */
    @PostMapping("/project/pledges/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CreatePledgeResponse> createPledge(
            @PathVariable Long userId,
            @Valid @RequestBody CreatePledgeRequest request) {
        return ApiResult.success("프로젝트 후원 참여에 성공했습니다.", service.createPledge(userId, request));
    }

    /**
     * 후원 상세 조회
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return message, pledgeDetail
     */
    @GetMapping("/pledges/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetPledgeDetailResponse> getPledgeDetail(@PathVariable Long pledgeId) {
        return ApiResult.success("후원 상세 조회에 성공했습니다.", service.getPledgeDetail(pledgeId));
    }

    /**
     * 후원 취소
     *
     * @param pledgeId 해당 후원 아이디
     * @return message
     */
    @DeleteMapping("/pledges/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> deletePledge(@PathVariable Long pledgeId) {
        service.deletePledge(pledgeId);

        return ApiResult.success("후원 취소에 성공했습니다.");
    }
    
    /**
     * 보상 이행 상태 갱신
     *
     * @param pledgeId 해당 후원 아이디
     * @param request  이행 상태 정보
     * @return message, updatedInfo
     */
    @PatchMapping("/pledges/{pledgeId}/fulfillment")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PatchFulfillmentResponse> patchFulfillment(
            @PathVariable Long pledgeId,
            @Valid @RequestBody PatchFulfillmentRequest request) {
        return ApiResult.success("보상 이행 상태 갱신에 성공했습니다.", service.patchFulfillment(pledgeId, request));
    }


}
