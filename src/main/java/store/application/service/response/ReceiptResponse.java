package store.application.service.response;

public record ReceiptResponse(
        PurchaseArea purchaseArea,
        GiftArea giftArea,
        MembershipArea membershipArea,
        int payAmount
) {
}
