package io.github.authservice.crowdfund.feature.pledges.response;

import java.util.List;

public record GetAllPledgesResponse(
        String message,
        List<PledgeSummary> pledges
) {
}
