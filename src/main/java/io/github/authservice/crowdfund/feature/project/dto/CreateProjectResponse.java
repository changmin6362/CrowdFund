package io.github.authservice.crowdfund.feature.project.dto;

/**
 * 프로젝트 생성 결과 응답 객체.
 * 생성된 프로젝트의 식별 번호와 성공 메시지를 포함함.
 */
public record CreateProjectResponse(
        Long id,                // 생성된 프로젝트 식별 번호
        String message          // 결과 메시지
) {
}