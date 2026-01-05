package store.domain;

public class PromotionProduct {
    private final Promotion promotion;
    private final Stock stock;

    public PromotionProduct(final Promotion promotion, final int stock) {
        this.promotion = promotion;
        this.stock = new Stock(stock);
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getStock() {
        return stock.value();
    }
}
