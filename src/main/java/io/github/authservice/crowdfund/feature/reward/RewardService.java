package io.github.authservice.crowdfund.feature.reward;

import io.github.authservice.crowdfund.domain.reward.Reward;
import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.domain.reward.mapper.RewardMapper;
import io.github.authservice.crowdfund.feature.reward.request.CreateRewardRequest;
import io.github.authservice.crowdfund.feature.reward.request.PatchRewardReqeust;
import io.github.authservice.crowdfund.feature.reward.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RewardService {

    private final RewardRepository repository;
    private final RewardMapper mapper;

    /**
     * 프로젝트에 리워드 등록 도메인 로직
     */
    @Transactional
    public CreateRewardResponse createReward(@Valid Long projectId, CreateRewardRequest request) {

        Reward reward = new Reward(
                null,
                projectId,
                request.title(),
                request.description(),
                request.price(),
                request.stock(),
                null
        );

        Reward savedReward = repository.save(reward);

        RewardInfo rewardInfo = new RewardInfo(
                savedReward.id(),
                savedReward.projectId(),
                savedReward.title(),
                savedReward.description(),
                savedReward.price(),
                savedReward.stock(),
                savedReward.createdAt()
        );

        return new CreateRewardResponse("리워드가 성공적으로 생성되었습니다", rewardInfo);
    }

    /**
     * 프로젝트의 리워드 목록 조회
     */
    public GetRewardsResponse getReward(@Valid Long projectId) {

        List<RewardInfo> rewards = repository.findByProjectId(projectId);

        return new GetRewardsResponse(
                "리워드 목록 조회가 성공적으로 완료되었습니다",
                rewards
        );
    }

    /**
     * 리워드 수정 도메인 로직
     */
    @Transactional
    public PatchRewardResponse patchReward(Long rewardId, PatchRewardReqeust request) {
        Reward reward = repository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("리워드를 찾을 수 없습니다."));

        Reward updatedReward = new Reward(
                reward.id(),
                reward.projectId(),
                request.title(),
                request.description(),
                request.price(),
                request.stock(),
                reward.createdAt()
        );

        Reward savedReward = repository.save(updatedReward);

        RewardInfo rewardInfo = new RewardInfo(
                savedReward.id(),
                savedReward.projectId(),
                savedReward.title(),
                savedReward.description(),
                savedReward.price(),
                savedReward.stock(),
                savedReward.createdAt()
        );

        return new PatchRewardResponse(
                "리워드 수정이 성공적으로 완료되었습니다",
                rewardInfo
        );
    }

    /**
     * 리워드 삭제 도메인 로직
     */
    @Transactional
    public DeleteRewardResponse deleteReward(@Valid Long rewardId) {

        repository.deleteById(rewardId);

        return new DeleteRewardResponse(
                "리워드 삭제가 성공적으로 완료되었습니다",
                rewardId
        );
    }
}
