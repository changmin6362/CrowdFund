package io.github.crowdfund.feature.pledge.admin.dto.detail;

import io.swagger.v3.oas.annotations.media.Schema;

public record AdminPledgeDetailResponse(
        @Schema(description = "관리자용 후원 상세 정보")
        AdminPledgeDetail adminPledgeDetail
) {
}
