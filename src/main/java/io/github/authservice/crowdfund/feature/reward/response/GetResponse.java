package io.github.authservice.crowdfund.feature.reward.response;

import java.util.List;

/**
 * 리워드 조회 응답 데이터
 *
 * message : 응답 메시지
 * reward : 조회된 리워드 목록 데이터
 */
public record GetResponse(

        String message,

        List<RewardInfo> reward


) {
}