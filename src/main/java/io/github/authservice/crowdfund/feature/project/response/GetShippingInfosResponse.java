package io.github.authservice.crowdfund.feature.project.response;

import java.util.List;

public record GetShippingInfosResponse(
        List<ShippingInfo> shippingInfos
) {
}