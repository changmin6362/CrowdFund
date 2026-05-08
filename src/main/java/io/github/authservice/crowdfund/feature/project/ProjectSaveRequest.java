package io.github.authservice.crowdfund.feature.project;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

/**
 * 프로젝트 데이터 영속화 및 요청 매핑용 객체.
 * 데이터베이스 테이블 구조와 1:1로 대응되는 핵심 데이터 모델.
 * Spring Data JDBC의 식별자 매핑을 위해 @Id 어노테이션 사용.
 * 설계 지침 준수를 위한 Record 타입 구성.
 */
public record ProjectSaveRequest(
        @Id
        Long id,                // 프로젝트 식별 번호 (Primary Key)

        String title,           // 프로젝트 제목

        String description,     // 프로젝트 상세 설명

        Long goalAmount,        // 목표 펀딩 금액

        LocalDateTime startAt,  // 펀딩 시작 일시

        LocalDateTime endAt,    // 펀딩 종료 일시

        Long categoryId,        // 소속 카테고리 식별 번호

        Long creatorId          // 프로젝트 생성자(User) 식별 번호
) {
}