package io.github.authservice.crowdfund.domain.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * 사용자 테이블 매핑용 엔티티
 *
 * @param id        사용자 ID
 * @param email     이메일주소 (최대 50자)
 * @param password  비밀번호 (해시 처리)
 * @param nickname  별명 (최대 12자)
 * @param name      이름 (최대 6자)
 * @param phone     전화번호 (최대 13자)
 * @param role      권한 [USER: 일반 사용자, ADMIN: 관리자]
 * @param createdAt 생성일시
 */
@Table("user")
public record User(
    @Id Long id,
    String email,
    String password,
    String nickname,
    String name,
    String phone,
    String role,
    LocalDateTime createdAt
) {}
