package io.github.crowdfund.feature.comment.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CommentInfo(
        @Schema(description = "댓글 ID", example = "1")
        Long commentId,

        @Schema(description = "작성자 이름", example = "작성자 이름 예시")
        String writerName,

        @Schema(description = "댓글 내용", example = "댓글 내용 예시")
        String content,

        @Schema(description = "댓글 생성일시", example = "2023-09-01T12:00:00")
        LocalDateTime createdAt,

        @Schema(description = "댓글 수정 가능 여부", example = "true")
        boolean isEditable
) {
}