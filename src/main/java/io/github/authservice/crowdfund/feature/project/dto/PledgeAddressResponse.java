package io.github.authservice.crowdfund.feature.project.dto;

/**
 * 후원자 배송지 정보 응답 객체.
 */
public record PledgeAddressResponse(
        Long pledgeId,      // 후원 식별 번호
        String receiverName, // 받는 사람 이름
        String zipCode,      // 우편번호
        String address,      // 주소
        String detailAddress // 상세 주소
) {
}