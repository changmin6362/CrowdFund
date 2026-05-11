package io.github.authservice.crowdfund.domain.user;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 사용자 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
    /**
     * 이메일로 사용자를 조회합니다.
     *
     * @param email 사용자 이메일
     * @return 사용자 정보 (존재하지 않을 경우 빈 Optional)
     */
    Optional<User> findByEmail(String email);
}
