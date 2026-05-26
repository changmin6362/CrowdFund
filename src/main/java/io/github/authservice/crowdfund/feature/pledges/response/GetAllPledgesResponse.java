package io.github.authservice.crowdfund.feature.pledges.response;

import java.util.List;

public record GetAllPledgesResponse(
        List<PledgeSummary> pledges
) {
}
