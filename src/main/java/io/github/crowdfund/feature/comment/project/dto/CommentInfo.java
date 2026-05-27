package io.github.crowdfund.feature.comment.project.dto;

import java.time.LocalDateTime;

public record CommentInfo(
        Long commentId,
        String writerName,
        String content,
        LocalDateTime createdAt,
        boolean isEditable
) {
}