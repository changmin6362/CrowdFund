package io.github.authservice.crowdfund.feature.comment.response;

import java.util.List;

public record GetMyCommentsResponse(
        String message,
        List<MyCommentInfo> myComments
) {
}