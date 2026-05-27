package io.github.crowdfund.feature.project.creator.dto.extract;

import java.util.List;

public record CreatorShippingInfosExtractResponse(
        List<ShippingInfo> shippingInfos
) {
}