package io.github.authservice.crowdfund.domain.payment;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 결제 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface PaymentRepository extends ListCrudRepository<Payment, Long> {
    /**
     * 후원 ID로 결제 내역을 조회합니다.
     *
     * @param pledgeId 후원 ID
     * @return 결제 내역 (존재하지 않을 경우 빈 Optional)
     */
    Optional<Payment> findByPledgeId(Long pledgeId);
}
