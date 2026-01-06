package store.domain.product;

import store.common.error.DomainException;
import store.common.error.ErrorMessage;
import store.common.error.InsufficientStockException;

import java.util.Objects;

public class Stock {
    private static final int MIN_VALUE = 0;

    private final int stock;

    public Stock(final int stock) {
        validateMinRange(stock);
        this.stock = stock;
    }

    private void validateMinRange(final int value) {
        if (value < MIN_VALUE) {
            throw new DomainException(ErrorMessage.BELOW_MIN_VALUE, MIN_VALUE);
        }
    }

    public Stock withDecrease(int requestQuantity) {
        if (stock - requestQuantity < MIN_VALUE) {
            throw new InsufficientStockException(ErrorMessage.LACK_OF_PROMOTION_PRODUCT_STOCK, requestQuantity - stock);
        }
        return new Stock(stock - requestQuantity);
    }

    public int insufficientQuantity(int requestQuantity) {
        return requestQuantity - stock;
    }

    public int value() {
        return stock;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Stock integerMaxVO = (Stock) object;
        return stock == integerMaxVO.stock;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(stock);
    }
}
