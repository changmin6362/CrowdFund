package io.github.authservice.crowdfund.feature.reward.response;

/**
 * 리워드 수정 응답 데이터
 *
 * message : 응답 메시지
 * reward : 수정된 리워드 데이터
 */
public record ModifyResponse(

        String message,

        Object reward

) {
}