package io.github.authservice.crowdfund.feature.comment.response;

import java.util.List;

public record GetCommentsResponse(
        List<CommentInfo> comments
) {
}