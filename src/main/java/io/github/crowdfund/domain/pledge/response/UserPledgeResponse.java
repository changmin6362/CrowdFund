package io.github.crowdfund.domain.pledge.response;

import org.apache.ibatis.type.Alias;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 내가 참여한 후원 정보 응답
 */
@Alias("UserPledgeResponse")
public record UserPledgeResponse(
        Long pledgeId,
        Long projectId,
        String projectTitle,
        Long rewardId,
        String rewardTitle,
        BigDecimal amount,
        String status,
        LocalDateTime pledgedAt
) {}
