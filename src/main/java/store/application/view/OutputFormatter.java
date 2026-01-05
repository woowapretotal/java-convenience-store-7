package store.application.view;

import store.application.service.response.*;

import java.util.List;
import java.util.stream.Collectors;

public final class OutputFormatter {
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String LINE_SEPARATOR = System.lineSeparator();

    private OutputFormatter() {
    }

    public static String formatErrorMessage(String message) {
        return ERROR_PREFIX + message;
    }

    public static String formatProducts(final List<ProductResponse> products) {
        return products.stream()
                .map(OutputFormatter::formatProduct)
                .collect(Collectors.joining(LINE_SEPARATOR));
    }

    private static String formatProduct(ProductResponse product) {
        String defaultFormat = "- %s %s %s";
        String promotionFormat = "- %s %s %s %s";

        String defaultFormatted = formatDefaultProduct(product, defaultFormat);

        if (product.promotionProductResponse() != null) {
            String promotionFormatted = formatPromotionProduct(product, promotionFormat);
            return promotionFormatted + LINE_SEPARATOR + defaultFormatted;
        }

        return defaultFormatted;
    }

    private static String formatDefaultProduct(final ProductResponse product, final String defaultFormat) {
        return defaultFormat.formatted(
                product.name(),
                MoneyFormatter.formatInteger(product.price()),
                formatStock(product.stock()));
    }

    private static String formatPromotionProduct(final ProductResponse product, final String promotionFormat) {
        return promotionFormat.formatted(
                product.name(),
                MoneyFormatter.formatInteger(product.price()),
                formatStock(product.promotionProductResponse().promotionStock()),
                product.promotionProductResponse().promotionName()
        );
    }

    private static String formatStock(final int stock) {
        if (stock == 0) {
            return "재고 없음";
        }
        return "%d개".formatted(stock);
    }

    public static String formatOrder(final OrderResponse order) {
        String orderHeader = "%-8s".formatted("상품명")
                + "%-6s".formatted("수량")
                + "%-6s".formatted("금액")
                + LINE_SEPARATOR;
        String formattedProductChunks = order.productChunks().stream()
                .map(OutputFormatter::formatOrderProductChunk)
                .collect(Collectors.joining(LINE_SEPARATOR));
        return orderHeader + formattedProductChunks;
    }

    private static String formatOrderProductChunk(final ProductChunk productChunk) {
        return "%-8s".formatted(productChunk.productName())
                + "%-6s".formatted(String.valueOf(productChunk.quantity()))
                + "%-6s".formatted(MoneyFormatter.formatInteger(productChunk.chunkPrice()));
    }

    public static String formatGift(final GiftResponse gift) {
        return gift.productChunks().stream()
                .map(OutputFormatter::formatGiftProductChunk)
                .collect(Collectors.joining(LINE_SEPARATOR));
    }

    private static String formatGiftProductChunk(final ProductChunk productChunk) {
        return "%-8s".formatted(productChunk.productName())
                + "%-6s".formatted(String.valueOf(productChunk.quantity()));
    }

    public static String formatPriceInfo(final ReceiptResponse receipt) {
        return String.join(LINE_SEPARATOR,
                formatTotalOrderAmount(receipt.orderResponse()),
                formatGiftAmount(receipt.giftResponse()),
                formatMemberShipAmount(receipt.membershipResponse()),
                formatPayAmount(receipt.payAmount())
        );
    }

    private static String formatTotalOrderAmount(final OrderResponse order) {
        int totalQuantity = order.productChunks().stream()
                .map(ProductChunk::quantity)
                .mapToInt(Integer::intValue)
                .sum();

        return "%-8s".formatted("총구매액")
                + "%-6s".formatted(String.valueOf(totalQuantity))
                + "%-6s".formatted(MoneyFormatter.formatInteger(order.totalOrderAmount()));
    }

    private static String formatGiftAmount(final GiftResponse giftResponse) {
        return "%-14s".formatted("행사할인")
                + "%-6s".formatted(MoneyFormatter.minusFormat(giftResponse.totalGiftAmount()));
    }

    private static String formatMemberShipAmount(final MembershipResponse membershipResponse) {
        return "%-14s".formatted("멤버십할인")
                + "%-6s".formatted(MoneyFormatter.minusFormat(membershipResponse.memberShipDiscountAmount()));
    }

    private static String formatPayAmount(final int payAmount) {
        return "%-14s".formatted("내실돈")
                + "%-6s".formatted(MoneyFormatter.formatInteger(payAmount));
    }
}
