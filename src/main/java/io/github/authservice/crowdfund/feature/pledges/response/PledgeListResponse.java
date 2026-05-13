package io.github.authservice.crowdfund.feature.pledges.response;

import java.util.List;

public record PledgeListResponse(
        String message,
        List<PledgeDetailResponse.PledgeDetail> pledge
) {
}
