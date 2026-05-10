package io.github.authservice.crowdfund.domain.pledgeaddress;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PledgeAddressRepository extends ListCrudRepository<PledgeAddress, Long> {
    Optional<PledgeAddress> findByPledgeId(Long pledgeId);
}
