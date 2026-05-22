package io.github.authservice.crowdfund.feature.comment.response;

public record MyCommentInfo(
        Long commentId,
        Long projectId,
        String projectTitle,
        String content,
        String createdAt
) {
}