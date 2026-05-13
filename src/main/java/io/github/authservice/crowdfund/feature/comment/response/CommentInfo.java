package io.github.authservice.crowdfund.feature.comment.response;

/**
 * 댓글 정보 응답 데이터
 *
 * commentId : 댓글 ID
 * content : 댓글 내용
 * writerName : 작성자 이름
 * createdAt : 작성일시
 */
public record CommentInfo(

        Long commentId,
        String content,
        String writerName,
        String createdAt

) {
}