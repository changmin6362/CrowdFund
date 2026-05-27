package io.github.authservice.crowdfund.feature.comment.project.dto.fetch;

import io.github.authservice.crowdfund.feature.comment.project.dto.CommentInfo;

import java.util.List;

public record ProjectCommentsFetchResponse(
        List<CommentInfo> comments
) {
}