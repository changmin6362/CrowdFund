package io.github.authservice.crowdfund.feature.comment.response;

public record PatchCommentResponse(
        String message,
        CommentInfo patchedComment
) {
}