package io.github.crowdfund.feature.pledge.creator;

import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.feature.pledge.creator.dto.fulfill.FulfillmentInfo;
import io.github.crowdfund.feature.pledge.creator.dto.fulfill.CreatorPledgeFulfillRequest;
import io.github.crowdfund.feature.pledge.creator.dto.fulfill.CreatorPledgeFulfillResponse;
import io.github.crowdfund.global.security.SecurityUser;
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
    public CreatorPledgeFulfillResponse fulfill(SecurityUser securityUser, Long pledgeId, @Valid CreatorPledgeFulfillRequest request) {
        Pledge pledge = pledgeRepository.findById(pledgeId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 후원 내역입니다."));

        projectRepository.validateProjectOwner(pledge.projectId(), securityUser.getUserId());

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
}
