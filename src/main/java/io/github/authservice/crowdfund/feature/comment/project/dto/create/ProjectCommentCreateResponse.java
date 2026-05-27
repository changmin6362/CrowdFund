package io.github.authservice.crowdfund.feature.comment.project.dto.create;

import io.github.authservice.crowdfund.feature.comment.project.dto.CommentInfo;

public record ProjectCommentCreateResponse(
        CommentInfo createdComment
) {
}