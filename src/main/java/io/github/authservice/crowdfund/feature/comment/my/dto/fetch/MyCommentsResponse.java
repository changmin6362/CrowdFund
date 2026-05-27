package io.github.authservice.crowdfund.feature.comment.my.dto.fetch;

import java.util.List;

public record MyCommentsResponse(
        List<MyCommentInfo> myComments
) {
}