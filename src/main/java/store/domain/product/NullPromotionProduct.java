package store.domain.product;

import store.domain.result.GiftAppliedQuantity;
import store.domain.result.PromotionApplyResult;

public class NullPromotionProduct extends PromotionProduct {

    public NullPromotionProduct() {
        super(null, 0);
    }

    @Override
    public PromotionApplyResult purchase(final int requestQuantity, final boolean ignorePromotionAddition, final boolean ignoreInsufficientPromotionStock) {
        return new PromotionApplyResult(GiftAppliedQuantity.noGift(), requestQuantity);
    }

    @Override
    public Promotion getPromotion() {
        return super.getPromotion();
    }

    @Override
    public int getStock() {
        return super.getStock();
    }
}
