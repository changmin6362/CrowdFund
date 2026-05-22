package io.github.authservice.crowdfund.feature.comment.response;

import java.time.LocalDateTime;

public record CommentInfo(
        Long commentId,
        String writerName,
        String content,
        LocalDateTime createdAt,
        boolean isEditable
) {
}