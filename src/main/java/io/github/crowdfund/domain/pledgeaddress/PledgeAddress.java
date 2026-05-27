package io.github.crowdfund.domain.pledgeaddress;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 후원 시점 배송지 주소 테이블 매핑용 엔티티
 *
 * @param id             후원 주소 ID
 * @param pledgeId       후원 ID
 * @param userId         후원자 ID (조회용)
 * @param recipientName  수령인명 (최대 20자)
 * @param phone          전화번호 (최대 13자)
 * @param postalCode     우편번호 (최대 5자)
 * @param addressMain    기본 주소 (최대 100자)
 * @param addressDetail  상세 주소 (최대 100자)
 * @param createdAt      생성일시
 * @param updatedAt      수정일시
 */
@Table("pledge_address")
public record PledgeAddress(
    @Id Long id,
    Long pledgeId,
    Long userId,
    String recipientName,
    String phone,
    String postalCode,
    String addressMain,
    String addressDetail,
    LocalDateTime createdAt,
    LocalDateTime updatedAt

) {}
