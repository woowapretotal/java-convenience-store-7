package store.domain.product;

import store.domain.result.GiftAppliedQuantity;
import store.domain.result.PurchasedProduct;

public class PurchaseProduct {
    private final Product product;
    private final int requestQuantity;

    public PurchaseProduct(final Product product, final int requestQuantity) {
        this.product = product;
        this.requestQuantity = requestQuantity;
    }

    public PurchasedProduct purchase(final boolean ignorePromotionAddition, final boolean ignoreInsufficientPromotionStock) {
        int originAmount = product.calculateChunkAmount(requestQuantity);
        GiftAppliedQuantity giftAppliedQuantity = product.purchase(requestQuantity, ignorePromotionAddition, ignoreInsufficientPromotionStock);
        int giftChunkAmount = product.calculateChunkAmount(giftAppliedQuantity.getGiftQuantity());

        return new PurchasedProduct(
                product, requestQuantity, originAmount, giftAppliedQuantity.getGiftQuantity(),
                giftChunkAmount, giftAppliedQuantity.getPromotionAppliedQuantity());
    }

    public PurchaseProduct withAddFreeQuantity(int addQuantity) {
        return new PurchaseProduct(product, requestQuantity + addQuantity);
    }

    public PurchaseProduct withDecreaseQuantity(int decreaseQuantity) {
        return new PurchaseProduct(product, requestQuantity - decreaseQuantity);
    }

    public String getProductName() {
        return product.getName();
    }

    public int getChunkPrice() {
        return product.getPrice() * requestQuantity;
    }

    public int getRequestQuantity() {
        return requestQuantity;
    }
}
