package io.github.crowdfund.feature.comment.my.dto.fetch;

import java.time.LocalDateTime;

public record MyCommentInfo(
        Long commentId,
        Long projectId,
        String projectTitle,
        String content,
        LocalDateTime createdAt
) {
}