package io.github.authservice.crowdfund.feature.comment.response;

import java.util.List;

public record GetCommentsResponse(
        String message,
        List<CommentInfo> comments
) {
}