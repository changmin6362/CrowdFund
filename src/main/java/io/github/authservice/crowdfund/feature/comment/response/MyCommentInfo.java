package io.github.authservice.crowdfund.feature.comment.response;

import java.time.LocalDateTime;

public record MyCommentInfo(
        Long commentId,
        Long projectId,
        String projectTitle,
        String content,
        LocalDateTime createdAt
) {
}