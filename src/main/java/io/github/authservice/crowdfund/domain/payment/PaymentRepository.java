package io.github.authservice.crowdfund.domain.payment;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends ListCrudRepository<Payment, Long> {
    Optional<Payment> findByPledgeId(Long pledgeId);
}
