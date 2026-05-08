package io.github.authservice.crowdfund.feature.project.dto;

/**
 * 프로젝트 수정 및 상태 변경 결과 응답 객체.
 */
public record UpdateProjectResponse(
        Long id,                // 수정된 프로젝트 식별 번호
        String status,          // 변경된 프로젝트 상태
        String message          // 결과 메시지
) {
}