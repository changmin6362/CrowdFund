package io.github.authservice.crowdfund.feature.pledges.user;

import io.github.authservice.crowdfund.feature.pledges.user.dto.fulfill.UserPledgeFulfillRequest;
import io.github.authservice.crowdfund.feature.pledges.user.dto.fulfill.UserPledgeFulfillResponse;
import io.github.authservice.crowdfund.feature.pledges.user.dto.create.UserPledgeCreateRequest;
import io.github.authservice.crowdfund.feature.pledges.user.dto.create.UserPledgeCreateResponse;
import io.github.authservice.crowdfund.feature.pledges.user.dto.detail.UserPledgeDetailResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserPledgeController {

    private final UserPledgeService service;

    /**
     * 프로젝트 후원 참여
     *
     * @param userId  유저 아이디
     * @param request 후원 정보
     * @return message, pledgeId
     */
    @PostMapping("/project/pledges/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<UserPledgeCreateResponse> create(
            @PathVariable Long userId,
            @Valid @RequestBody UserPledgeCreateRequest request) {
        return ApiResult.success("프로젝트 후원 참여에 성공했습니다.", service.create(userId, request));
    }

    /**
     * 후원 상세 조회
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return message, pledgeDetail
     */
    @GetMapping("/pledges/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserPledgeDetailResponse> detail(@PathVariable Long pledgeId) {
        return ApiResult.success("후원 상세 조회에 성공했습니다.", service.detail(pledgeId));
    }

    /**
     * 후원 취소
     *
     * @param pledgeId 해당 후원 아이디
     * @return message
     */
    @DeleteMapping("/pledges/{pledgeId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<Void> cancel(@PathVariable Long pledgeId) {
        service.cancel(pledgeId);

        return ApiResult.success("후원 취소에 성공했습니다.");
    }

    /**
     * 보상 이행
     *
     * @param pledgeId 해당 후원 아이디
     * @param request  이행 상태 정보
     * @return message, updatedInfo
     */
    @PatchMapping("/pledges/{pledgeId}/fulfill")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<UserPledgeFulfillResponse> fulfill(
            @PathVariable Long pledgeId,
            @Valid @RequestBody UserPledgeFulfillRequest request) {
        return ApiResult.success("보상 이행 상태 갱신에 성공했습니다.", service.fulfill(pledgeId, request));
    }


}
