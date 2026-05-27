package io.github.crowdfund.feature.comment.project.dto.update;

import io.github.crowdfund.feature.comment.project.dto.CommentInfo;

public record ProjectCommentUpdateResponse(
        CommentInfo patchedComment
) {
}