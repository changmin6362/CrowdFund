package io.github.crowdfund.feature.pledges.creator;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.Pledge;
import io.github.crowdfund.domain.pledge.PledgeRepository;
import io.github.crowdfund.feature.pledges.creator.dto.fulfill.FulfillmentInfo;
import io.github.crowdfund.feature.pledges.creator.dto.fulfill.CreatorPledgeFulfillRequest;
import io.github.crowdfund.feature.pledges.creator.dto.fulfill.CreatorPledgeFulfillResponse;
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

    /**
     * 보상 이행 도메인 로직
     */
    @Transactional
    public CreatorPledgeFulfillResponse fulfill(Long pledgeId, @Valid CreatorPledgeFulfillRequest request) {
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

        return new CreatorPledgeFulfillResponse(new FulfillmentInfo(pledgeId, request.fulfillmentStatus(), fulfilledAt));
    }
}
