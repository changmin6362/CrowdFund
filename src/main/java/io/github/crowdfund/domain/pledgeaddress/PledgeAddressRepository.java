package io.github.crowdfund.domain.pledgeaddress;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 후원 시점 배송지 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface PledgeAddressRepository extends ListCrudRepository<PledgeAddress, Long> {
    /**
     * 후원 ID로 배송지 정보를 조회합니다.
     *
     * @param pledgeId 후원 ID
     * @return 배송지 정보 (존재하지 않을 경우 빈 Optional)
     */
    Optional<PledgeAddress> findByPledgeId(Long pledgeId);
}
