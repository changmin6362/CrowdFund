package io.github.crowdfund.feature.comment.my.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record MyCommentInfo(
        @Schema(description = "댓글 ID")
        Long commentId,

        @Schema(description = "프로젝트 ID")
        Long projectId,

        @Schema(description = "프로젝트 제목")
        String projectTitle,

        @Schema(description = "댓글 내용")
        String content,

        @Schema(description = "댓글 생성일시")
        LocalDateTime createdAt
) {
}