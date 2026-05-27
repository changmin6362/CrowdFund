package io.github.crowdfund.domain.useraddress;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 사용자의 배송지 테이블 매핑용 엔티티
 *
 * @param id             배송지 ID
 * @param userId         회원 ID
 * @param recipientName  수령인명 (최대 20자)
 * @param phone          전화번호 (최대 13자)
 * @param postalCode     우편번호 (최대 5자)
 * @param addressMain    기본 주소 (최대 100자)
 * @param addressDetail  상세 주소 (최대 100자)
 * @param isDefault      기본 배송지 여부
 * @param createdAt      생성일시
 * @param updatedAt      수정일시
 */
@Table("user_address")
public record UserAddress(
    @Id Long id,
    Long userId,
    String recipientName,
    String phone,
    String postalCode,
    String addressMain,
    String addressDetail,
    Boolean isDefault,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
