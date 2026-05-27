package io.github.authservice.crowdfund.feature.comment.project.dto.update;

import io.github.authservice.crowdfund.feature.comment.project.dto.CommentInfo;

public record ProjectCommentUpdateResponse(
        CommentInfo patchedComment
) {
}