package io.github.authservice.crowdfund.feature.comment.response;

public record CreateCommentResponse(
        String message,
        CommentInfo CreatedComment
) {
}