package io.github.crowdfund.feature.comment.project.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record CommentInfo(
        @Schema(description = "댓글 ID")
        Long commentId,

        @Schema(description = "작성자 이름")
        String writerName,

        @Schema(description = "댓글 내용")
        String content,

        @Schema(description = "댓글 생성일시")
        LocalDateTime createdAt,

        @Schema(description = "댓글 수정 가능 여부")
        boolean isEditable
) {
}