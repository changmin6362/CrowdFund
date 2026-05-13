package io.github.authservice.crowdfund.feature.comment.response;

import java.util.List;

/**
 * 내 댓글 목록 조회 응답 데이터
 *
 * message : 응답 메시지
 * commentList : 내가 작성한 댓글 목록
 */
public record MyCommentListResponse(

        String message,
        List<CommentInfo> commentList

) {
}