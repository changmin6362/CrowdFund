package io.github.authservice.crowdfund.feature.reward;

import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.feature.reward.request.CreateRewardRequest;
import io.github.authservice.crowdfund.feature.reward.request.PatchRewardReqeust;
import io.github.authservice.crowdfund.feature.reward.response.CreateRewardResponse;
import io.github.authservice.crowdfund.feature.reward.response.DeleteRewardResponse;
import io.github.authservice.crowdfund.feature.reward.response.GetRewardsResponse;
import io.github.authservice.crowdfund.feature.reward.response.PatchRewardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository repository;

    /**
     * 프로젝트에 리워드 등록 도메인 로직
     */
    public CreateRewardResponse createReward(@Valid Long projectId, CreateRewardRequest request) {
        // return new AddRewardResponse("리워드가 성공적으로 추가생성되었습니다",
        //         repository.addReward(projectId, request));
        return new CreateRewardResponse("리워드 추가 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 프로젝트의 리워드 목록 조회
     */
    public GetRewardsResponse getReward(@Valid Long projectId) {
        // return new GetResponse(
        //         "리워드 조회가 성공적으로 완료되었습니다",
        //         repository.getReward(projectId)
        // );
        return new GetRewardsResponse("리워드 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 리워드 수정 도메인 로직
     */
    public PatchRewardResponse patchReward(Long rewardId, PatchRewardReqeust request) {
        // return new ModifyResponse(
        //         "리워드 수정이 성공적으로 완료되었습니다",
        //         repository.modifyReward(rewardId, request)
        // );
        return new PatchRewardResponse("리워드 수정 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 리워드 삭제 도메인 로직
     */
    public DeleteRewardResponse deleteReward(@Valid Long rewardId) {
        // return new DeleteResponse(
        //         "리워드 삭제가 성공적으로 완료되었습니다",
        //         repository.deleteReward(rewardId)
        // );
        return new DeleteRewardResponse("리워드 삭제 기능은 구현되지 않았습니다.");
    }
}
