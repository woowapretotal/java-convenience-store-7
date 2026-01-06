package store.domain.product;

import store.common.error.DomainException;
import store.common.error.ErrorMessage;

import java.util.Objects;

public class Price {
    private static final int MIN_VALUE = 0;

    private final int value;

    public Price(final int value) {
        validateMinRange(value);
        this.value = value;
    }

    private void validateMinRange(final int value) {
        if (value < MIN_VALUE) {
            throw new DomainException(ErrorMessage.BELOW_MIN_VALUE, MIN_VALUE);
        }
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Price integerMaxVO = (Price) object;
        return value == integerMaxVO.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
