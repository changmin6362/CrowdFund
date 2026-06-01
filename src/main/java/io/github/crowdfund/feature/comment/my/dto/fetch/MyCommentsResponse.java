package io.github.crowdfund.feature.comment.my.dto.fetch;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MyCommentsResponse(
        @Schema(description = "내 댓글 목록")
        List<MyCommentInfo> myComments
) {
}