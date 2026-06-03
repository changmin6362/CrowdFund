package io.github.crowdfund.domain.user;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
