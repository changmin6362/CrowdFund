package io.github.authservice.crowdfund.feature.reward;

import io.github.authservice.crowdfund.feature.reward.request.CreateRewardRequest;
import io.github.authservice.crowdfund.feature.reward.request.PatchRewardReqeust;
import io.github.authservice.crowdfund.feature.reward.response.CreateRewardResponse;
import io.github.authservice.crowdfund.feature.reward.response.DeleteRewardResponse;
import io.github.authservice.crowdfund.feature.reward.response.GetRewardsResponse;
import io.github.authservice.crowdfund.feature.reward.response.PatchRewardResponse;
import io.github.authservice.crowdfund.global.common.ApiResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService service;

    /**
     * 프로젝트에 리워드 등록
     *
     * @param projectId 프로젝트아이디
     * @param request   리워드 추가 요청 데이터
     * @return message 메시지
     */
    @PostMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResult<CreateRewardResponse> createReward(@Valid @PathVariable Long projectId, @RequestBody CreateRewardRequest request) {
        return ApiResult.success("리워드 등록에 성공했습니다.", service.createReward(projectId, request));
    }

    /**
     * 프로젝트의 리워드 목록 조회
     *
     * @param projectId 프로젝트아이디
     * @return message, rewards
     */
    @GetMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<GetRewardsResponse> getRewards(@PathVariable Long projectId) {
        return ApiResult.success("리워드 목록 조회에 성공했습니다.", service.getReward(projectId));
    }

    /**
     * 리워드 수정
     *
     * @param rewardId 프로젝트아이디
     * @param request  리워드 수정 요청 데이터
     * @return message
     */
    @PatchMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<PatchRewardResponse> patchReward(@PathVariable @Valid Long rewardId, @RequestBody PatchRewardReqeust request) {
        return ApiResult.success("리워드 수정에 성공했습니다.", service.patchReward(rewardId, request));
    }

    /**
     * 리워드 삭제
     *
     * @param rewardId 프로젝트아이디
     * @return message 메시지
     */
    @DeleteMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResult<DeleteRewardResponse> deleteReward(@PathVariable Long rewardId) {
        return ApiResult.success("리워드 삭제에 성공했습니다.", service.deleteReward(rewardId));
    }
}

