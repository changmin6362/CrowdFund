package io.github.authservice.crowdfund.feature.project.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProjectInfo(
        Long projectId,         // 팀장님 피드백 반영: id -> projectId로 변경
        String title,           // 프로젝트 제목
        String description,     // 프로젝트 상세 설명
        BigDecimal goalAmount,  // 팀장님 피드백 반영: 큰 금액은 BigDecimal 사용
        BigDecimal currentAmount, // 팀장님 피드백 반영: 큰 금액은 BigDecimal 사용
        LocalDateTime startAt,  // 펀딩 시작 일시
        LocalDateTime endAt,    // 펀딩 종료 일시
        String status,          // 프로젝트 진행 상태
        Integer categoryId,     // 팀장님 피드백 반영: 카테고리는 Integer로 변경
        Long creatorId
) {}
