package io.github.crowdfund.domain.pledge.response;

import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 내가 참여한 후원 정보 응답
 */
@Alias("UserPledgeResponse")
public record UserPledgeResponse(
        @Schema(description = "유저가 후원한 후원 ID")
        Long pledgeId,

        @Schema(description = "유저가 후원한 프로젝트 ID")
        Long projectId,

        @Schema(description = "유저가 후원한 프로젝트 제목")
        String projectTitle,

        @Schema(description = "유저가 후원한 리워드 ID")
        Long rewardId,

        @Schema(description = "유저가 후원한 리워드 제목")
        String rewardTitle,

        @Schema(description = "유저가 후원한 금액")
        BigDecimal amount,

        @Schema(description = "유저가 후원한 상태")
        String status,

        @Schema(description = "유저가 후원한 날짜")
        LocalDateTime pledgedAt
) {}
