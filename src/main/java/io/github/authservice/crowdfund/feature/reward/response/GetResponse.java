package io.github.authservice.crowdfund.feature.reward.response;

/**
 * 리워드 조회 응답 데이터
 *
 * message : 응답 메시지
 * rewardList : 조회된 리워드 목록 데이터
 */
public record GetResponse(

        String message,

        Object rewardList

) {
}