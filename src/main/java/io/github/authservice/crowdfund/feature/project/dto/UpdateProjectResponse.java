package io.github.authservice.crowdfund.feature.project.dto;

/**
 * 프로젝트 수정 및 상태 변경 결과 응답 객체.
 */
public record UpdateProjectResponse(
        Long projectId,         // 팀장님 피드백 반영: id -> projectId로 변경
        String status,          // 변경된 프로젝트 상태
        String message          // 결과 메시지
) {
}