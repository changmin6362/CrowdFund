package io.github.authservice.crowdfund.feature.comment.response;

/**
 * 댓글 수정 응답 데이터
 *
 * message : 응답 메시지
 * comment : 수정된 댓글 데이터
 */
public record PatchCommentResponse(

        String message,

        CommentInfo comment

) {
}