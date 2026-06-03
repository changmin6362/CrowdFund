package io.github.crowdfund.feature.pledge.creator;

import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.feature.pledge.creator.dto.fulfill.FulfillmentInfo;
import io.github.crowdfund.feature.pledge.creator.dto.fulfill.CreatorPledgeFulfillRequest;
import io.github.crowdfund.feature.pledge.creator.dto.fulfill.CreatorPledgeFulfillResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CreatorPledgeService {

    private final PledgeRepository pledgeRepository;
    private final io.github.crowdfund.domain.project.ProjectRepository projectRepository;

    /**
     * 보상 이행 상태 변경 도메인 로직
     */
    @Transactional
    public CreatorPledgeFulfillResponse fulfill(Long creatorId, Long pledgeId, @Valid CreatorPledgeFulfillRequest request) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        validateProjectOwner(creatorId, pledge.projectId());

        // request의 fulfillmentStatus에 따라 Pledge 상태 변경
        Pledge updatedPledge = switch (request.fulfillmentStatus()) {
            case FULFILLED -> pledge.completeFulfillment(LocalDateTime.now());
            case READY -> pledge.cancelFulfillment();
        };

        Pledge savedPledge = pledgeRepository.save(updatedPledge);

        return new CreatorPledgeFulfillResponse(new FulfillmentInfo(
                savedPledge.id(),
                savedPledge.fulfillmentStatus(),
                savedPledge.fulfilledAt()
        ));
    }

    private void validateProjectOwner(Long creatorId, Long projectId) {
        projectRepository.findById(projectId)
                .ifPresentOrElse(project -> {
                    if (!project.creatorId().equals(creatorId)) {
                        throw new IllegalArgumentException("본인의 프로젝트만 관리할 수 있습니다.");
                    }
                }, () -> {
                    throw new IllegalArgumentException("존재하지 않는 프로젝트입니다.");
                });
    }
}
