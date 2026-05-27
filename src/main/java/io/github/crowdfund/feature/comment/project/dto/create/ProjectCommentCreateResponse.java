package io.github.crowdfund.feature.comment.project.dto.create;

import io.github.crowdfund.feature.comment.project.dto.CommentInfo;

public record ProjectCommentCreateResponse(
        CommentInfo createdComment
) {
}