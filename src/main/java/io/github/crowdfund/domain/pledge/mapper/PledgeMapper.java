package io.github.crowdfund.domain.pledge.mapper;

import io.github.crowdfund.domain.pledge.FulfillmentStatus;
import io.github.crowdfund.domain.pledge.PledgeStatus;
import io.github.crowdfund.feature.pledge.my.dto.fetch.MyPledgeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 후원 관련 복합 쿼리를 담은 매퍼 (MyBatis)
 */
@Mapper
public interface PledgeMapper {
    /**
     * 특정 사용자가 참여한 후원 목록을 조회합니다.
     */
    List<MyPledgeInfo> findPledgesByUserId(
            @Param("userId") Long userId,
            @Param("fulfillmentStatus") FulfillmentStatus fulfillmentStatus,
            @Param("pledgeStatus") PledgeStatus pledgeStatus,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            @Param("limit") Integer limit
    );
}
