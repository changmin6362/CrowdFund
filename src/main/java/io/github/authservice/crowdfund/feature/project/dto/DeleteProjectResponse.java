package io.github.authservice.crowdfund.feature.project.dto;

/**
 * 프로젝트 삭제 결과 응답 객체.
 */
public record DeleteProjectResponse(
        Long id,                // 삭제된 프로젝트 식별 번호
        String message          // 결과 메시지
) {
}