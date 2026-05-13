package io.github.authservice.crowdfund.feature.project.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 특정 프로젝트 상세 조회 응답 객체.
 * 팀장님 피드백 반영: message를 최상단으로 배치하고 상세 정보를 내부 레코드로 분리함.
 */
public record GetProjectDetailResponse(
        String message,              // 결과 메시지 (첫 번째 순서 준수)
        ProjectDetailInfo project   // 상세 프로젝트 정보 객체
) {
    /**
     * 상세정보 조회를 위해서 리워드 정보도 포함하도록 수정할 예정
     */
    public record ProjectDetailInfo(
            Long projectId,          // 프로젝트 식별 번호
            String title,            // 프로젝트 제목
            String description,      // 상세 설명
            BigDecimal goalAmount,   // 목표 금액 (BigDecimal 적용)
            BigDecimal currentAmount, // 현재 후원 금액
            LocalDateTime startAt,   // 시작 일시
            LocalDateTime endAt,     // 종료 일시
            String status,           // 현재 상태
            Integer categoryId,      // 카테고리 ID (Integer 적용)
            Long creatorId           // 창작자 ID
    ) {}
}