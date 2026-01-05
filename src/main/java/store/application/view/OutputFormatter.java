package store.application.view;

import store.application.service.response.ProductResponse;

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
}
