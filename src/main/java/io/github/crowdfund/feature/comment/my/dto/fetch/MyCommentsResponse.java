package io.github.crowdfund.feature.comment.my.dto.fetch;

import io.github.crowdfund.global.common.dto.pagination.CursorRequest;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public record MyCommentsResponse(
        @Schema(description = "내 댓글 목록")
        List<MyCommentInfo> myComments,

        @Schema(description = "다음 페이지 존재 여부", example = "true")
        boolean hasNext,

        @Schema(description = "다음 페이지 커서 정보")
        CursorRequest nextCursor
) {
}