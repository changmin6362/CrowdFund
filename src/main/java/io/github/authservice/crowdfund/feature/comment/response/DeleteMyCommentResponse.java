package io.github.authservice.crowdfund.feature.comment.response;

public record DeleteMyCommentResponse(
        String message,
        Long deletedCommentId
) {
}