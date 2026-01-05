package store.application.service.response;

public record ReceiptResponse(
        OrderResponse orderResponse,
        GiftResponse giftResponse,
        MembershipResponse membershipResponse,
        int payAmount
) {
}
