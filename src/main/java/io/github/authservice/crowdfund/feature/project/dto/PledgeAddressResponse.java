package io.github.authservice.crowdfund.feature.project.dto;

/**
 * 후원자 배송지 정보 응답 객체.
 */
public record PledgeAddressResponse(
        Long pledgeId,        // 후원 식별 번호
        String recipientName, // 팀장님 피드백: recipient_name -> recipientName
        String postalCode,    // 팀장님 피드백: postal_code -> postalCode
        String addressMain,   // 팀장님 피드백: address_main -> addressMain
        String addressDetail  // 팀장님 피드백: address_detail -> addressDetail
) {
}