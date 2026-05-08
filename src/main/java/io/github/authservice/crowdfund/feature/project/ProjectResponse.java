package io.github.authservice.crowdfund.feature.project;

import java.time.LocalDateTime;

/**
 * 프로젝트 정보 응답용 데이터 객체.
 * 목록 및 상세 조회 시 클라이언트에게 전달할 데이터 구조 정의.
 * 설계 지침 준수를 위한 Record 타입 구성.
 */
public record ProjectResponse(
        Long id,                // 프로젝트 식별 번호

        String title,           // 프로젝트 제목

        String description,     // 프로젝트 상세 설명

        Long goalAmount,        // 목표 펀딩 금액

        Long currentAmount,     // 현재 모인 금액

        LocalDateTime startAt,  // 펀딩 시작 일시

        LocalDateTime endAt,    // 펀딩 종료 일시

        String status,          // 프로젝트 진행 상태

        Long categoryId,        // 소속 카테고리 식별 번호

        Long creatorId          // 프로젝트 생성자 식별 번호
) {
}