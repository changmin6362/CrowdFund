package io.github.authservice.crowdfund.feature.pledges;

import io.github.authservice.crowdfund.domain.pledge.PledgeRepository;
import io.github.authservice.crowdfund.feature.pledges.request.PledgeRequest;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeDeleteResponse;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeDetailResponse;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeListResponse;
import io.github.authservice.crowdfund.feature.pledges.response.PledgeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PledgeService {

    private final PledgeRepository pledgeRepository;

    /**
     * 특정 후원 상세 조회 도메인 로직
     *
     * @param pledgeId 해당 후원 아이디
     * @return 메세지, 해당 후원 상세 정보
     */
    public PledgeDetailResponse getPledgeDetail(Long pledgeId) {
        // return new PledgeDetailResponse("펀딩 상세 정보를 성공적으로 불러왔습니다.", pledgeRepository.getPledgeDetail(pledgeId));
        return new PledgeDetailResponse("펀딩 상세 정보 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 특정 후원 삭제 도메인 로직
     *
     * @param pledgeId 해당 후원 아이디
     * @return 메세지
     */
    public PledgeDeleteResponse deletePledge(Long pledgeId) {
        // pledgeRepository.deletePledge(pledgeId);
        // return new PledgeDeleteResponse("펀딩 주문을 성공적으로 취소했습니다.");
        return new PledgeDeleteResponse("펀딩 주문 취소 기능은 구현되지 않았습니다.");
    }

    /**
     * 후원 정보 리스트 조회 도메인 로직
     *
     * @return 메세지, 모든 후원 정보
     */
    public PledgeListResponse getAllPledges() {
        // return new PledgeListResponse("펀딩 리스트를 성공적으로 불러왔습니다.", pledgeRepository.getAllPledges());
        return new PledgeListResponse("펀딩 리스트 조회 기능은 구현되지 않았습니다.", null);
    }

    /**
     * 후원 주문 생성 도메인 로직
     *
     * @ userId 현재 로그인 되어있는 유저 아이디
     * @ projectId 해당 프로젝트 아이디
     * @ rewardId 프로젝트에서 선택한 리워드 아이디
     * @return 메세지
     */
    public PledgeResponse createPledge(@Valid PledgeRequest request) {
        // return new PledgeResponse("펀딩 후원이 성공하였습니다", pledgeRepository.createPledge(request));
        return new PledgeResponse("펀딩 후원 기능은 구현되지 않았습니다.", null, null, null);
    }
}
