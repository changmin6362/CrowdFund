package io.github.authservice.crowdfund.feature.pledges.response;

public record PatchFulfillmentResponse(
        String message,
        FulfillmentInfo updatedInfo
) {
}
