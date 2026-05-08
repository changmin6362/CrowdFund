package io.github.authservice.crowdfund.feature.project;

import java.time.LocalDateTime;

/**
 * 프로젝트 정보 응답용 데이터 객체.
 * 목록 조회 및 상세 페이지 등 클라이언트에게 데이터를 전달할 때 사용.
 * 설계 지침에 따라 Record 타입으로 구현.
 */
public record ProjectResponse(
        Long id,                // 프로젝트 식별값
        String title,           // 제목
        String description,     // 상세 설명
        Long goalAmount,        // 목표 금액
        Long currentAmount,     // 현재 모인 금액
        LocalDateTime startAt,  // 시작 일시
        LocalDateTime endAt,    // 종료 일시
        String status,          // 프로젝트 상태
        Long categoryId,        // 카테고리 식별값
        Long creatorId          // 창작자 식별값
) {
}