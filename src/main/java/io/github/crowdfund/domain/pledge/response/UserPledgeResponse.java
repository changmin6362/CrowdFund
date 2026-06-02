package io.github.crowdfund.domain.pledge.response;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 내가 참여한 후원 정보 응답
 */
@Alias("UserPledgeResponse")
public record UserPledgeResponse(
        @Schema(description = "유저가 후원한 후원 ID", example = "1")
        Long pledgeId,

        @Schema(description = "유저가 후원한 프로젝트 ID", example = "2")
        Long projectId,

        @Schema(description = "유저가 후원한 프로젝트 제목", example = "프로젝트 제목 예시")
        String projectTitle,

        @Schema(description = "유저가 후원한 리워드 ID", example = "1")
        Long rewardId,

        @Schema(description = "유저가 후원한 리워드 제목", example = "리워드 제목 예시")
        String rewardTitle,

        @Schema(description = "유저가 후원한 금액", example = "35000")
        BigDecimal amount,

        @Schema(description = "유저가 후원한 상태")
        PledgeStatus status,

        @Schema(description = "유저 보상 이행 상태")
        FulfillmentStatus fulfillmentStatus,

        @Schema(description = "유저가 후원한 날짜", example = "2023-08-01T12:00:00")
        LocalDateTime pledgedAt
) {}
