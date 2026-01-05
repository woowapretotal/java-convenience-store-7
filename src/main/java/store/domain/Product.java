package store.domain;

import java.util.Objects;

public class Product {
    private final String name;
    private final Price price;
    private final Stock stock;
    private PromotionProduct promotionProduct;

    public Product(final String name, final int price, final int stock, final PromotionProduct promotionProduct) {
        this.name = name;
        this.price = new Price(price);
        this.stock = new Stock(stock);
        this.promotionProduct = promotionProduct;
    }

    public static Product defaultProduct(final String name, final int price, final int stock) {
        return new Product(name, price, stock, null);
    }

    public static Product onlyPromotionProduct(final String name, final int price, PromotionProduct promotionProduct) {
        return new Product(name, price, 0, promotionProduct);
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price.value();
    }

    public int getStock() {
        return stock.value();
    }

    public void registerPromotionProduct(PromotionProduct promotionProduct) {
        this.promotionProduct = promotionProduct;
    }

    public PromotionProduct getPromotionProduct() {
        return promotionProduct;
    }

    public boolean isSameName(String productName) {
        return this.name.equals(productName);
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Product product = (Product) object;
        return Objects.equals(getName(), product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
