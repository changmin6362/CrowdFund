package io.github.crowdfund.feature.pledges.admin.dto.fetch;

import java.util.List;

public record AdminPledgesFetchResponse(
        List<PledgeSummary> pledges
) {
}
