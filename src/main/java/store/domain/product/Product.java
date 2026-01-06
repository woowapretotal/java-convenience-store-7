package store.domain.product;

import store.common.error.ApplicationException;
import store.common.error.ErrorMessage;
import store.common.error.GiftQuantityAskException;
import store.common.error.InsufficientPromotionStockAskException;
import store.domain.result.GiftAppliedQuantity;
import store.domain.result.PromotionApplyResult;

import java.util.Objects;

public class Product {
    private final String name;
    private final Price price;
    private Stock stock;
    private PromotionProduct promotionProduct;

    public Product(final String name, final int price, final int stock, PromotionProduct promotionProduct) {
        this.name = name;
        this.price = new Price(price);
        this.stock = new Stock(stock);
        if (promotionProduct == null) {
            promotionProduct = new NullPromotionProduct();
        }
        this.promotionProduct = promotionProduct;
    }

    public static Product defaultProduct(final String name, final int price, final int stock) {
        return new Product(name, price, stock, new NullPromotionProduct());
    }

    public static Product onlyPromotionProduct(final String name, final int price, PromotionProduct promotionProduct) {
        return new Product(name, price, 0, promotionProduct);
    }

    public GiftAppliedQuantity purchase(final int requestQuantity, final boolean ignorePromotionAddition, final boolean ignoreInsufficientPromotionStock) {
        validateEnoughStock(requestQuantity);

        PromotionApplyResult promotionApplyResult = null;
        try {
            promotionApplyResult = promotionProduct.purchase(requestQuantity, ignorePromotionAddition, ignoreInsufficientPromotionStock);
        } catch (GiftQuantityAskException e) {
            e.registerItemName(name);
            throw e;
        } catch (InsufficientPromotionStockAskException e) {
            e.registerItemName(name);
            throw e;
        }

        stock = stock.withDecrease(promotionApplyResult.getLeftQuantity());
        return promotionApplyResult.getGiftQuantity();
    }

    private void validateEnoughStock(final int requestQuantity) {
        if (requestQuantity > stock.value() + promotionProduct.getStock()) {
            throw new ApplicationException(ErrorMessage.LACK_OF_PRODUCT_STOCK);
        }
    }

    public int calculateChunkAmount(int quantity) {
        return price.value() * quantity;
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
