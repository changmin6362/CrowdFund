package io.github.authservice.crowdfund.feature.project.dto;

/**
 * 프로젝트 삭제 결과 응답 객체.
 */
public record DeleteProjectResponse(
        Long projectId,         // 팀장님 피드백 반영: id -> projectId로 변경
        String message          // 결과 메시지
) {
}