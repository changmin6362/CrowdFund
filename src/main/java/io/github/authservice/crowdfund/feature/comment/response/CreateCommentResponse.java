package io.github.authservice.crowdfund.feature.comment.response;

/**
 * 댓글 작성 응답 데이터
 *
 * message : 응답 메시지
 * comment : 생성된 댓글 데이터
 */
public record CreateCommentResponse(

        String message,

        CommentInfo comment

) {
}