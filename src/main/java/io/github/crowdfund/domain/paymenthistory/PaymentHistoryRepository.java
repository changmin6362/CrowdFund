package io.github.crowdfund.domain.paymenthistory;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 결제 상태 변경 이력 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface PaymentHistoryRepository extends ListCrudRepository<PaymentHistory, Long> {
    /**
     * 결제 ID로 상태 변경 이력을 조회합니다.
     */
    List<PaymentHistory> findByPaymentIdOrderByChangedAtDesc(Long paymentId);
}
