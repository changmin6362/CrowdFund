package io.github.crowdfund.feature.reward.user;

import io.github.crowdfund.domain.reward.RewardRepository;
import io.github.crowdfund.feature.reward.user.dto.fetch.RewardFetchInfo;
import io.github.crowdfund.feature.reward.user.dto.fetch.UserRewardsFetchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserRewardService {

    private final RewardRepository repository;

    /**
     * 프로젝트의 리워드 목록 조회
     */
    @Transactional
    public UserRewardsFetchResponse fetch(@Valid Long projectId) {

        List<RewardFetchInfo> rewards = repository.findByProjectId(projectId).stream()
                .map(reward -> new RewardFetchInfo(
                        reward.id(),
                        reward.projectId(),
                        reward.title(),
                        reward.description(),
                        reward.price(),
                        reward.stock(),
                        reward.createdAt()

                )).toList();

        return new UserRewardsFetchResponse(rewards);
    }
}
