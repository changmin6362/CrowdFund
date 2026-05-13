package io.github.authservice.crowdfund.feature.reward.response;

/**
 * 리워드 생성 응답 데이터
 *
 * message : 응답 메시지
 * reward : 생성된 리워드 데이터
 */
public record AddRewardResponse(

        String message,

        RewardInfo reward

) {
}