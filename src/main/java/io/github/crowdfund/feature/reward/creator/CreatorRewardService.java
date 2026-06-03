package io.github.crowdfund.feature.reward.creator;

import io.github.crowdfund.domain.reward.Reward;
import io.github.crowdfund.domain.reward.RewardRepository;
import io.github.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateRequest;
import io.github.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateResponse;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateResponse;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateReqeust;
import io.github.crowdfund.feature.reward.creator.dto.RewardInfo;
import io.github.crowdfund.feature.reward.creator.dto.delete.CreatorRewardDeleteResponse;
import io.github.crowdfund.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatorRewardService {

    private final RewardRepository repository;
    private final io.github.crowdfund.domain.project.ProjectRepository projectRepository;

    /**
     * 프로젝트에 리워드 등록 도메인 로직
     */
    @Transactional
    public CreatorRewardCreateResponse create(SecurityUser securityUser, @Valid Long projectId, CreatorRewardCreateRequest request) {
        projectRepository.validateProjectOwner(projectId, securityUser.getUserId());

        Reward reward = new Reward(
                null,
                projectId,
                request.title(),
                request.description(),
                request.price(),
                request.stock(),
                LocalDateTime.now()
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

        return new CreatorRewardCreateResponse(rewardInfo);
    }

    /**
     * 리워드 수정 도메인 로직
     */
    @Transactional
    public CreatorRewardUpdateResponse update(SecurityUser securityUser, Long rewardId, CreatorRewardUpdateReqeust request) {
        Reward reward = repository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("리워드를 찾을 수 없습니다."));

        projectRepository.validateProjectOwner(reward.projectId(), securityUser.getUserId());

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

        return new CreatorRewardUpdateResponse(rewardInfo);
    }

    /**
     * 리워드 삭제 도메인 로직
     */
    @Transactional
    public CreatorRewardDeleteResponse delete(SecurityUser securityUser, @Valid Long rewardId) {
        Reward reward = repository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("리워드를 찾을 수 없습니다."));

        projectRepository.validateProjectOwner(reward.projectId(), securityUser.getUserId());

        repository.deleteById(rewardId);

        return new CreatorRewardDeleteResponse(rewardId);
    }
}
