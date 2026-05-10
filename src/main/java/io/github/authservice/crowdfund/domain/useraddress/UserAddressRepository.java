package io.github.authservice.crowdfund.domain.useraddress;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAddressRepository extends ListCrudRepository<UserAddress, Long> {
    List<UserAddress> findByUserId(Long userId);
}
