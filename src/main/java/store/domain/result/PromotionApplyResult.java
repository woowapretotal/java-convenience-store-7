package store.domain.result;

public class PromotionApplyResult {
    private final GiftAppliedQuantity giftAppliedQuantity;
    private final int leftQuantity;

    public PromotionApplyResult(final GiftAppliedQuantity giftAppliedQuantity, final int leftQuantity) {
        this.giftAppliedQuantity = giftAppliedQuantity;
        this.leftQuantity = leftQuantity;
    }

    public static PromotionApplyResult noPromotion(int leftQuantity) {
        return new PromotionApplyResult(GiftAppliedQuantity.noGift(), leftQuantity);
    }

    public static PromotionApplyResult allPromotionStock(GiftAppliedQuantity giftAppliedQuantity) {
        return new PromotionApplyResult(giftAppliedQuantity, 0);
    }

    public GiftAppliedQuantity getGiftQuantity() {
        return giftAppliedQuantity;
    }

    public int getLeftQuantity() {
        return leftQuantity;
    }
}
