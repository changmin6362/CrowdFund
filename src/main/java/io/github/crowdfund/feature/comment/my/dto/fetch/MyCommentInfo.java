package io.github.crowdfund.feature.comment.my.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record MyCommentInfo(
        @Schema(description = "댓글 ID", example = "1")
        Long commentId,

        @Schema(description = "프로젝트 ID", example = "1")
        Long projectId,

        @Schema(description = "프로젝트 제목", example = "프로젝트 제목 예시")
        String projectTitle,

        @Schema(description = "댓글 내용", example = "프로젝트의 댓글 예시")
        String content,

        @Schema(description = "댓글 생성일시", example = "2023-09-15T12:00:00")
        LocalDateTime createdAt
) {
}