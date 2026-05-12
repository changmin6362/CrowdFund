package io.github.authservice.crowdfund.feature.reward;

import io.github.authservice.crowdfund.feature.reward.request.AddRewardRequest;
import io.github.authservice.crowdfund.feature.reward.request.ModifyRequest;
import io.github.authservice.crowdfund.feature.reward.response.AddRewardResponse;
import io.github.authservice.crowdfund.feature.reward.response.DeleteResponse;
import io.github.authservice.crowdfund.feature.reward.response.GetResponse;
import io.github.authservice.crowdfund.feature.reward.response.ModifyResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository repository;

    /** 리워드 생성 성공 응답 반환
     *
     * @param projectId
     * @param request
     * @return message
     */
    public AddRewardResponse addReward(@Valid Long projectId, AddRewardRequest request) {
        return new AddRewardResponse("리워드가 성공적으로 추가생성되었습니다",
                repository.addReward(projectId, request));
    }

    /**
     * 리워드 조회 성공 응답 반환
     * @param projectId
     * @return message, rewardList
     */
    public GetResponse getReward(@Valid Long projectId) {
        return new GetResponse(
                "리워드 조회가 성공적으로 완료되었습니다",
                repository.getReward(projectId)
        );
    }

    /**
     * 리워드 수정 성공 응답 반환
     * @param rewardId
     * @param request
     * @return message
     */
    public ModifyResponse modifyReward(Long rewardId, ModifyRequest request) {
        return new ModifyResponse(
                "리워드 수정이 성공적으로 완료되었습니다",
                repository.modifyReward(rewardId, request)
        );
    }

    /**
     * 리워드 삭제 성공 응답 반환
     * @param rewardId
     * @return message
     */
    public DeleteResponse deleteReward(@Valid Long rewardId) {
        return new DeleteResponse(
                "리워드 삭제가 성공적으로 완료되었습니다",
                repository.deleteReward(rewardId)
        );
    }
}
