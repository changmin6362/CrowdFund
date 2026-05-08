package io.github.authservice.crowdfund.feature.reward;

import io.github.authservice.crowdfund.feature.reward.request.AddRequest;
import io.github.authservice.crowdfund.feature.reward.request.ModifyRequest;
import io.github.authservice.crowdfund.feature.reward.response.AddResponse;
import io.github.authservice.crowdfund.feature.reward.response.DeleteResponse;
import io.github.authservice.crowdfund.feature.reward.response.GetResponse;
import io.github.authservice.crowdfund.feature.reward.response.ModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param projectId
     * @param request
     * @return message
     */
    @PostMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.CREATED)
    public AddResponse addReward(@Valid @PathVariable("projectId") Long projectId, @RequestBody AddRequest request) {
        return rewardService.addReward(projectId, request);
    }

    /**
     * 프로젝트 리워드 목록
     *
     * @param projectId
     * @return message, rewardList
     */
    @GetMapping("/projects/{projectId}/rewards")
    @ResponseStatus(HttpStatus.OK)
    public GetResponse getReward(@PathVariable("projectId") Long projectId) {
        return rewardService.GetReward(projectId);
    }

    /**
     * 리워드 수정
     *
     * @param rewardId
     * @param request
     * @return message
     */
    @PatchMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public ModifyResponse modifyReward(@Valid @PathVariable("rewardId") Long rewardId, @RequestBody ModifyRequest request) {
        return rewardService.ModifyReward(rewardId, request);
    }

    /**
     * 리워드 삭제
     *
     * @param rewardId
     * @return message
     */
    @DeleteMapping("/rewards/{rewardId}")
    @ResponseStatus(HttpStatus.OK)
    public DeleteResponse deleteReward(@PathVariable("rewardId") Long rewardId) {
        return rewardService.DeleteReward(rewardId);
    }
}

