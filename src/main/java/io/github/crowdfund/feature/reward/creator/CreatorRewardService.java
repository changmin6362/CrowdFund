package io.github.crowdfund.feature.reward.creator;

import io.github.crowdfund.domain.reward.Reward;
import io.github.crowdfund.domain.reward.RewardRepository;
import io.github.crowdfund.feature.reward.creator.dto.RewardInfo;
import io.github.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateRequest;
import io.github.crowdfund.feature.reward.creator.dto.create.CreatorRewardCreateResponse;
import io.github.crowdfund.feature.reward.creator.dto.delete.CreatorRewardDeleteResponse;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateRequest;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateResponse;
import io.github.crowdfund.feature.reward.creator.dto.update.CreatorRewardUpdateStockRequest;
import io.github.crowdfund.global.security.SecurityUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatorRewardService {

    private final RewardRepository repository;
    private final io.github.crowdfund.domain.project.ProjectRepository projectRepository;
    private final io.github.crowdfund.domain.pledge.PledgeRepository pledgeRepository;

    /**
     * 프로젝트에 리워드 등록 도메인 로직
     */
    @Transactional
    public CreatorRewardCreateResponse create(SecurityUser securityUser, @Valid Long projectId, CreatorRewardCreateRequest request) {
        io.github.crowdfund.domain.project.Project project = getValidatedProject(projectId, securityUser.getUserId());

        if (!project.isOngoing()) {
            throw new IllegalArgumentException("진행 중인 프로젝트에만 리워드를 등록할 수 있습니다.");
        }

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

        return new CreatorRewardCreateResponse(toRewardInfo(savedReward));
    }

    /**
     * 리워드 정보 수정 도메인 로직
     */
    @Transactional
    public CreatorRewardUpdateResponse update(SecurityUser securityUser, Long rewardId, CreatorRewardUpdateRequest request) {
        Reward reward = getReward(rewardId);
        io.github.crowdfund.domain.project.Project project = getValidatedProject(reward.projectId(), securityUser.getUserId());

        if (!project.isOngoing()) {
            throw new IllegalArgumentException("진행 중인 프로젝트에만 리워드를 수정할 수 있습니다.");
        }

        if (pledgeRepository.existsByRewardId(rewardId)) {
            throw new IllegalArgumentException("후원자가 있는 리워드는 수정할 수 없습니다.");
        }

        boolean isChanged =
                !Objects.equals(reward.title(), request.title()) ||
                        !Objects.equals(reward.description(), request.description()) ||
                        reward.price().compareTo(request.price()) != 0;

        if (!isChanged) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        Reward updatedReward = new Reward(
                reward.id(),
                reward.projectId(),
                request.title(),
                request.description(),
                request.price(),
                reward.stock(),
                reward.createdAt()
        );

        Reward savedReward = repository.save(updatedReward);

        return new CreatorRewardUpdateResponse(toRewardInfo(savedReward));
    }

    /**
     * 리워드 재고 수정 도메인 로직
     */
    @Transactional
    public CreatorRewardUpdateResponse updateStock(SecurityUser securityUser, Long rewardId, CreatorRewardUpdateStockRequest request) {
        Reward reward = getReward(rewardId);
        io.github.crowdfund.domain.project.Project project = getValidatedProject(reward.projectId(), securityUser.getUserId());

        if (!project.isOngoing()) {
            throw new IllegalArgumentException("진행 중인 프로젝트에만 리워드 재고를 수정할 수 있습니다.");
        }

        if (Objects.equals(reward.stock(), request.stock())) {
            throw new IllegalArgumentException("수정할 내용이 없습니다.");
        }

        long pledgeCount = pledgeRepository.countByRewardId(rewardId);
        if (request.stock() != null && request.stock() < pledgeCount) {
            throw new IllegalArgumentException("재고는 후원자 수(" + pledgeCount + ")보다 적을 수 없습니다.");
        }

        Reward updatedReward = new Reward(
                reward.id(),
                reward.projectId(),
                reward.title(),
                reward.description(),
                reward.price(),
                request.stock(),
                reward.createdAt()
        );

        Reward savedReward = repository.save(updatedReward);

        return new CreatorRewardUpdateResponse(toRewardInfo(savedReward));
    }

    /**
     * 리워드 삭제 도메인 로직
     */
    @Transactional
    public CreatorRewardDeleteResponse delete(SecurityUser securityUser, @Valid Long rewardId) {
        Reward reward = getReward(rewardId);
        getValidatedProject(reward.projectId(), securityUser.getUserId());

        repository.deleteById(rewardId);

        return new CreatorRewardDeleteResponse(rewardId);
    }

    private Reward getReward(Long rewardId) {
        return repository.findById(rewardId)
                .orElseThrow(() -> new IllegalArgumentException("리워드를 찾을 수 없습니다."));
    }

    private io.github.crowdfund.domain.project.Project getValidatedProject(Long projectId, Long userId) {
        io.github.crowdfund.domain.project.Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 프로젝트입니다."));
        project.validateOwner(userId);
        return project;
    }

    private RewardInfo toRewardInfo(Reward reward) {
        return new RewardInfo(
                reward.id(),
                reward.projectId(),
                reward.title(),
                reward.description(),
                reward.price(),
                reward.stock(),
                reward.createdAt()
        );
    }
}
