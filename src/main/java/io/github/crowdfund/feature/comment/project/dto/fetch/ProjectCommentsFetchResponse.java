package io.github.crowdfund.feature.comment.project.dto.fetch;

import io.github.crowdfund.feature.comment.project.dto.CommentInfo;

import java.util.List;

public record ProjectCommentsFetchResponse(
        List<CommentInfo> comments
) {
}