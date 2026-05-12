package io.github.authservice.crowdfund.feature.reward.response;

/**
 * 리워드 정보 응답 데이터
 *
 * rewardId : 리워드 ID
 * title : 리워드 제목
 * description : 리워드 설명
 * price : 리워드 가격
 * stock : 리워드 재고
 */
public record RewardInfo(

        Long rewardId,
        String title,
        String description,
        Integer price,
        Integer stock

) {
}