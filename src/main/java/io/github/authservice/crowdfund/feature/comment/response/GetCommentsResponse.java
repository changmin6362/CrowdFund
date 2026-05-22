package io.github.authservice.crowdfund.feature.comment.response;

import java.util.List;

/**
 * 특정 프로젝트 댓글 목록 조회 응답 데이터
 *
 * message : 응답 메시지
 * commentList : 댓글 목록
 */
public record GetCommentsResponse(

        String message,
        List<CommentInfo> commentList

) {
}