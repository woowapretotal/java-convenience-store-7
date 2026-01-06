package store.domain.result;

import store.common.error.DomainException;
import store.common.error.ErrorMessage;

import java.util.Objects;

public class GiftAppliedQuantity {
    private static final int MIN_VALUE = 0;

    private final int giftQuantity;
    private final int promotionAppliedQuantity;

    public GiftAppliedQuantity(final int giftQuantity, final int promotionAppliedQuantity) {
        validateMinRange(giftQuantity);
        this.giftQuantity = giftQuantity;
        this.promotionAppliedQuantity = promotionAppliedQuantity;
    }

    public static GiftAppliedQuantity noGift() {
        return new GiftAppliedQuantity(0, 0);
    }

    private void validateMinRange(final int value) {
        if (value < MIN_VALUE) {
            throw new DomainException(ErrorMessage.BELOW_MIN_VALUE, MIN_VALUE);
        }
    }

    public int getGiftQuantity() {
        return giftQuantity;
    }

    public int getPromotionAppliedQuantity() {
        return promotionAppliedQuantity;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        GiftAppliedQuantity integerMaxVO = (GiftAppliedQuantity) object;
        return giftQuantity == integerMaxVO.giftQuantity;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(giftQuantity);
    }
}
