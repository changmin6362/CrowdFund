package io.github.authservice.crowdfund.feature.reward.request;

/**
 * 리워드 조회 요청 데이터
 *
 * projectId : 조회할 프로젝트 ID
 */
public record GetRequest(

        Long projectId

) {
}