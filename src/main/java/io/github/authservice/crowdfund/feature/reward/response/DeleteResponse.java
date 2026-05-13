package io.github.authservice.crowdfund.feature.reward.response;

/**
 * 리워드 삭제 응답 데이터
 *
 * message : 응답 메시지
 * reward : 삭제된 리워드 데이터
 */
public record DeleteResponse(

        String message

) {
}