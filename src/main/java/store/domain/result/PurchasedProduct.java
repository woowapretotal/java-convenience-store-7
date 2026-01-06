package store.domain.result;

import store.domain.product.Product;

public class PurchasedProduct {
    private final Product product;
    private final int requestQuantity;
    private final int originChunkAmount;
    private final int giftQuantity;
    private final int giftChunkAmount;
    private final int promotionAppliedAmount;

    public PurchasedProduct(final Product product, final int requestQuantity, final int originChunkAmount,
                            final int giftQuantity, final int giftChunkAmount, final int promotionAppliedQuantity) {
        this.product = product;
        this.requestQuantity = requestQuantity;
        this.originChunkAmount = originChunkAmount;
        this.giftQuantity = giftQuantity;
        this.giftChunkAmount = giftChunkAmount;
        this.promotionAppliedAmount = product.calculateChunkAmount(promotionAppliedQuantity);
    }

    public int getOriginChunkAmount() {
        return originChunkAmount;
    }

    public String getProductName() {
        return product.getName();
    }

    public int getRequestQuantity() {
        return requestQuantity;
    }

    public int getGiftQuantity() {
        return giftQuantity;
    }

    public int getGiftChunkAmount() {
        return giftChunkAmount;
    }

    public int getPromotionAppliedAmount() {
        return promotionAppliedAmount;
    }
}
