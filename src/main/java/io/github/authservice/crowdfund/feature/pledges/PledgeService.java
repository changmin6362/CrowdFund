package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.authservice.crowdfund.domain.pledge.Pledge;
import io.github.authservice.crowdfund.domain.pledge.PledgeRepository;
import io.github.authservice.crowdfund.domain.project.Project;
import io.github.authservice.crowdfund.domain.project.ProjectRepository;
import io.github.authservice.crowdfund.domain.reward.Reward;
import io.github.authservice.crowdfund.domain.reward.RewardRepository;
import io.github.authservice.crowdfund.domain.user.User;
import io.github.authservice.crowdfund.domain.user.UserRepository;
import io.github.authservice.crowdfund.feature.pledges.request.CreatePledgeRequest;
import io.github.authservice.crowdfund.feature.pledges.request.UpdateFulfillmentRequest;
import io.github.authservice.crowdfund.feature.pledges.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PledgeService {

    private final PledgeRepository pledgeRepository;
    private final RewardRepository rewardRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    /**
     * 후원 목록 조회 도메인 로직
     */
    public GetAllPledgesResponse getAllPledges() {
        List<Pledge> pledges = pledgeRepository.findAll();
        List<PledgeSummary> summaries = pledges.stream()
                .map(this::mapToPledgeSummary)
                .collect(Collectors.toList());
        return new GetAllPledgesResponse("펀딩 리스트를 성공적으로 불러왔습니다.", summaries);
    }

    /**
     * 후원 참여 도메인 로직
     */
    @Transactional
    public CreatePledgeResponse createPledge(Long userId, @Valid CreatePledgeRequest request) {
        Reward reward = rewardRepository.findById(request.reward_id())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리워드입니다."));

        if (!reward.projectId().equals(request.project_id())) {
            throw new IllegalArgumentException("해당 프로젝트의 리워드가 아닙니다.");
        }

        // TODO: 재고(stock) 체크 로직 추가 필요 여부 확인 (현재 Reward에 stock 필드 존재)
        // if (reward.stock() <= 0) { throw new RuntimeException("재고가 부족합니다."); }

        Pledge pledge = new Pledge(
                null,
                userId,
                request.project_id(),
                request.reward_id(),
                reward.price().longValue(),
                FulfillmentStatus.READY,
                null,
                LocalDateTime.now()
        );

        Pledge savedPledge = pledgeRepository.save(pledge);

        return new CreatePledgeResponse("펀딩 후원이 성공하였습니다.", savedPledge.id());
    }

    /**
     * 후원 상세 조회 도메인 로직
     */
    public GetPledgeDetailResponse getPledgeDetail(Long pledgeId) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        return new GetPledgeDetailResponse("펀딩 상세 정보를 성공적으로 불러왔습니다.", mapToPledgeDetail(pledge));
    }

    /**
     * 후원 취소 도메인 로직
     */
    @Transactional
    public DeletePledgeResponse deletePledge(Long pledgeId) {
        if (!pledgeRepository.existsById(pledgeId)) {
            throw new IllegalArgumentException("존재하지 않는 후원 내역입니다.");
        }
        pledgeRepository.deleteById(pledgeId);
        return new DeletePledgeResponse("펀딩 주문을 성공적으로 취소했습니다.");
    }

    /**
     * 보상 이행 상태 갱신 도메인 로직
     */
    @Transactional
    public UpdateFulfillmentResponse updateFulfillment(Long pledgeId, @Valid UpdateFulfillmentRequest request) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        LocalDateTime fulfilledAt = pledge.fulfilledAt();
        if (request.fulfillmentStatus() == FulfillmentStatus.COMPLETED) {
            fulfilledAt = LocalDateTime.now();
        }

        Pledge updatedPledge = new Pledge(
                pledge.id(),
                pledge.userId(),
                pledge.projectId(),
                pledge.rewardId(),
                pledge.amount(),
                request.fulfillmentStatus(),
                fulfilledAt,
                pledge.createdAt()
        );

        pledgeRepository.save(updatedPledge);

        return new UpdateFulfillmentResponse("보상 이행 상태가 변경되었습니다.",
                new FulfillmentInfo(pledgeId, request.fulfillmentStatus(), fulfilledAt));
    }

    private PledgeDetail mapToPledgeDetail(Pledge pledge) {
        return new PledgeDetail(
                pledge.id(),
                pledge.userId(),
                pledge.projectId(),
                pledge.rewardId(),
                pledge.amount(),
                pledge.createdAt().toString()
        );
    }

    private PledgeSummary mapToPledgeSummary(Pledge pledge) {
        String userName = userRepository.findById(pledge.userId())
                .map(User::name)
                .orElseThrow(() -> new IllegalStateException("해당 유저를 찾을 수 없습니다. ID: " + pledge.userId()));

        String projectTitle = projectRepository.findById(pledge.projectId())
                .map(Project::title)
                .orElseThrow(() -> new IllegalStateException("해당 프로젝트를 찾을 수 없습니다. ID: " + pledge.projectId()));

        return new PledgeSummary(
                pledge.id(),
                pledge.userId(),
                userName,
                pledge.projectId(),
                projectTitle,
                pledge.rewardId(),
                pledge.amount(),
                pledge.fulfillmentStatus(),
                pledge.createdAt().toString()
        );
    }
}
