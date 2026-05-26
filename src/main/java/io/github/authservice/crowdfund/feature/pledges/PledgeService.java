package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.domain.pledge.PledgeRepository;
import io.github.authservice.crowdfund.feature.pledges.request.CreatePledgeRequest;
import io.github.authservice.crowdfund.feature.pledges.request.UpdateFulfillmentRequest;
import io.github.authservice.crowdfund.feature.pledges.response.DeletePledgeResponse;
import io.github.authservice.crowdfund.feature.pledges.response.GetPledgeDetailResponse;
import io.github.authservice.crowdfund.feature.pledges.response.GetAllPledgesResponse;
import io.github.authservice.crowdfund.feature.pledges.response.CreatePledgeResponse;
import io.github.authservice.crowdfund.feature.pledges.response.UpdateFulfillmentResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PledgeService {

    private final PledgeRepository pledgeRepository;

    /**
     * 후원 목록 조회 도메인 로직
     */
    public GetAllPledgesResponse getAllPledges() {
        // return new PledgeListResponse("펀딩 리스트를 성공적으로 불러왔습니다.", pledgeRepository.getAllPledges());
        return new GetAllPledgesResponse("펀딩 리스트 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 후원 참여 도메인 로직
     */
    public CreatePledgeResponse createPledge(Long userId, @Valid CreatePledgeRequest request) {
        // return new CreatePledgeResponse("펀딩 후원이 성공하였습니다", pledgeRepository.createPledge(userId, request));
        return new CreatePledgeResponse("펀딩 후원 기능은 구현되지 않았습니다.", null, null, null);
    }

    /**
     * 후원 상세 조회 도메인 로직
     */
    public GetPledgeDetailResponse getPledgeDetail(Long pledgeId) {
        // return new PledgeDetailResponse("펀딩 상세 정보를 성공적으로 불러왔습니다.", pledgeRepository.getPledgeDetail(pledgeId));
        return new GetPledgeDetailResponse("펀딩 상세 정보 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 후원 취소 도메인 로직
     */
    public DeletePledgeResponse deletePledge(Long pledgeId) {
        // pledgeRepository.deletePledge(pledgeId);
        // return new PledgeDeleteResponse("펀딩 주문을 성공적으로 취소했습니다.");
        return new DeletePledgeResponse("펀딩 주문 취소 기능은 구현되지 않았습니다.");
    }

    /**
     * 보상 이행 상태 갱신 도메인 로직
     */
    public UpdateFulfillmentResponse updateFulfillment(Long pledgeId, @Valid UpdateFulfillmentRequest request) {
        // Logic to update fulfillmentStatus and fulfilledAt in DB
        // If status is COMPLETED, set fulfilledAt to LocalDateTime.now()
        // return new UpdateFulfillmentResponse("보상 이행 상태가 변경되었습니다.", pledgeId, request.fulfillmentStatus(), LocalDateTime.now());
        return new UpdateFulfillmentResponse("보상 이행 상태 변경 기능은 구현되지 않았습니다.", pledgeId, request.fulfillmentStatus(), null);
    }


}
