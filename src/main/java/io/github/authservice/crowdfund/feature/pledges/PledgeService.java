package io.github.authservice.crowdfund.feature.pledges;

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
     * 특정 펀딩 상세 조회 도메인 로직
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return 메세지, 해당 펀딩 상세 정보
     */
    public PledgeDetailResponse getPledgeDetail(Long pledgeId) {
        return new PledgeDetailResponse("펀딩 상세 정보를 성공적으로 불러왔습니다.", pledgeRepository.getPledgeDetail(pledgeId));
    }

    /**
     * 특정 펀딩 삭제 도메인 로직
     *
     * @param pledgeId 해당 펀딩 아이디
     * @return 메세지
     */
    public PledgeDeleteResponse deletePledge(Long pledgeId) {
        pledgeRepository.deletePledge(pledgeId);

        return new PledgeDeleteResponse("펀딩 주문을 성공적으로 취소했습니다.");
    }

    /**
     * 펀딩 정보 리스트 조회 도메인 로직
     *
     * @return 메세지, 모든 펀딩 정보
     */
    public PledgeListResponse getAllPledges() {
        return new PledgeListResponse("펀딩 리스트를 성공적으로 불러왔습니다.", pledgeRepository.getAllPledges());
    }

    /**
     * 펀딩 주문 생성 도메인 로직
     *
     * @param userId 현재 로그인 되어있는 유저 아이디
     * @param projectId 해당 프로젝트 아이디
     * @param rewardId 프로젝트에서 선택한 리워드 아이디
     * @return 메세지
     */
    public PledgeResponse createPledge(@Valid PledgeRequest request) {
        return new PledgeResponse("펀딩 후원이 성공하였습니다", pledgeRepository.createPledge(request));
    }
}
