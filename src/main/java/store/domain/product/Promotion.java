package store.domain.product;

import store.common.error.ErrorMessage;
import store.common.error.GiftQuantityAskException;
import store.common.error.PromotionPeriodException;
import store.domain.result.GiftAppliedQuantity;

import java.time.LocalDate;

public class Promotion {
    private final String promotionName;
    private final int buyCount;
    private final int getCount;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(final String promotionName, final int buyCount, final int getCount, final LocalDate startDate, final LocalDate endDate) {
        this.promotionName = promotionName;
        this.buyCount = buyCount;
        this.getCount = getCount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public GiftAppliedQuantity calculateGiftCount(final LocalDate nowDate, final int quantity, final boolean ignorePromotionAddition) {
        validatePromotionPeriod(nowDate);

        if (!ignorePromotionAddition) {
            validateFreePromotionAdditionExists(quantity);
        }

        int giftQuantity = quantity / (buyCount + getCount);
        int promotionAppliedQuantity = giftQuantity * (buyCount + getCount);
        return new GiftAppliedQuantity(giftQuantity, promotionAppliedQuantity);
    }

    private void validateFreePromotionAdditionExists(final int quantity) {
        int remainQuantity = quantity % (buyCount + getCount);
        if (remainQuantity == quantity) {
            throw new GiftQuantityAskException(ErrorMessage.EXISTS_ADDITION_FREE, getCount);
        }
    }

    private void validatePromotionPeriod(final LocalDate nowDate) {
        if (!isPromotionPeriod(nowDate)) {
            throw new PromotionPeriodException(ErrorMessage.NOT_PROMOTION_PERIOD.message());
        }
    }

    private boolean isPromotionPeriod(final LocalDate nowDate) {
        return !nowDate.isBefore(startDate) && !nowDate.isAfter(endDate);
    }

    public String getPromotionName() {
        return promotionName;
    }

    public int getBuyCount() {
        return buyCount;
    }

    public int getGetCount() {
        return getCount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
