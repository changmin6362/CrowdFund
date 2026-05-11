package io.github.authservice.crowdfund.domain.useraddress;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 배송지 테이블에 대한 일반적인 CRUD 요청을 담은 레포지토리
 */
@Repository
public interface UserAddressRepository extends ListCrudRepository<UserAddress, Long> {
    /**
     * 특정 사용자의 모든 배송지 목록을 조회합니다.
     *
     * @param userId 회원 ID
     * @return 배송지 목록
     */
    List<UserAddress> findByUserId(Long userId);

    /**
     * 특정 사용자의 기본 배송지를 조회합니다.
     *
     * @param userId 회원 ID
     * @return 기본 배송지 정보 (존재하지 않을 경우 빈 Optional)
     */
    Optional<UserAddress> findByUserIdAndIsDefaultTrue(Long userId);
}
