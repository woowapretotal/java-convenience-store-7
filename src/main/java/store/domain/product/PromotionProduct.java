package store.domain.product;

import camp.nextstep.edu.missionutils.DateTimes;
import store.common.error.ErrorMessage;
import store.common.error.InsufficientPromotionStockAskException;
import store.common.error.InsufficientStockException;
import store.common.error.PromotionPeriodException;
import store.domain.result.GiftAppliedQuantity;
import store.domain.result.PromotionApplyResult;

public class PromotionProduct {
    private final Promotion promotion;
    private Stock stock;

    public PromotionProduct(final Promotion promotion, final int stock) {
        this.promotion = promotion;
        this.stock = new Stock(stock);
    }

    public PromotionApplyResult purchase(final int requestQuantity, final boolean ignorePromotionAddition, final boolean ignoreInsufficientPromotionStock) {
        try {
            GiftAppliedQuantity giftAppliedQuantity = promotion.calculateGiftCount(DateTimes.now().toLocalDate(), requestQuantity, ignorePromotionAddition);
            stock = stock.withDecrease(requestQuantity);
            return PromotionApplyResult.allPromotionStock(giftAppliedQuantity);
        } catch (PromotionPeriodException e) {
            return PromotionApplyResult.noPromotion(requestQuantity);
        } catch (InsufficientStockException e) {
            if (!ignoreInsufficientPromotionStock) {
                GiftAppliedQuantity giftAppliedQuantity = promotion.calculateGiftCount(DateTimes.now().toLocalDate(), stock.value(), ignorePromotionAddition);
                throw new InsufficientPromotionStockAskException(ErrorMessage.LACK_OF_PROMOTION_PRODUCT_STOCK, requestQuantity - giftAppliedQuantity.getPromotionAppliedQuantity());
            }
            int insufficientQuantity = stock.insufficientQuantity(requestQuantity);
            int decreasedQuantity = requestQuantity - insufficientQuantity;
            GiftAppliedQuantity giftAppliedQuantity = promotion.calculateGiftCount(DateTimes.now().toLocalDate(), decreasedQuantity, ignorePromotionAddition);
            stock = stock.withDecrease(decreasedQuantity);
            return new PromotionApplyResult(giftAppliedQuantity, insufficientQuantity);
        }
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public int getStock() {
        return stock.value();
    }
}
