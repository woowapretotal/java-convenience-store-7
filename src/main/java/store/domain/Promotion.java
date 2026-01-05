package store.domain;

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
