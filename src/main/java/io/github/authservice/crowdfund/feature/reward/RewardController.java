package io.github.authservice.crowdfund.feature.reward;

import io.github.authservice.crowdfund.feature.reward.request.AddRewardRequest;
import io.github.authservice.crowdfund.feature.reward.request.ModifyRequest;
import io.github.authservice.crowdfund.feature.reward.response.AddRewardResponse;
import io.github.authservice.crowdfund.feature.reward.response.DeleteResponse;
import io.github.authservice.crowdfund.feature.reward.response.GetResponse;
import io.github.authservice.crowdfund.feature.reward.response.ModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RewardController {

    private final RewardService rewardService;

    /**
     * 프로젝트에 리워드 추가
     *
     * @param projectId 프로젝트아이디
     * @param request   리워드 추가 요청 데이터
     * @return message 메시지
     */
    @PostMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.CREATED)
    public AddRewardResponse addReward(@Valid @PathVariable Long projectId, @RequestBody AddRewardRequest request) {
        return rewardService.addReward(projectId, request);
    }

    /**
     * 프로젝트 리워드 목록
     *
     * @param projectId 프로젝트아이디
     * @return message, rewardList 메시지, 리워드 목록
     */
    @GetMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.OK)
    public GetResponse getReward(@PathVariable Long projectId) {
        return rewardService.getReward(projectId);
    }

    /**
     * 리워드 수정
     *
     * @param rewardId 프로젝트아이디
     * @param request  리워드 수정 요청 데이터
     * @return message 메시지
     */
    @PatchMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public ModifyResponse modifyReward(@PathVariable @Valid Long rewardId, @RequestBody ModifyRequest request) {
        return rewardService.modifyReward(rewardId, request);
    }

    /**
     * 리워드 삭제
     *
     * @param rewardId 프로젝트아이디
     * @return message 메시지
     */
    @DeleteMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteResponse deleteReward(@PathVariable Long rewardId) {
        return rewardService.deleteReward(rewardId);
    }
}

