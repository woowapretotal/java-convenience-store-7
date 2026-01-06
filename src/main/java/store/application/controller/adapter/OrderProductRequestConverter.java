package store.application.controller.adapter;

import store.application.service.request.PurchaseProductRequest;
import store.common.error.ApplicationException;
import store.common.error.ErrorMessage;
import store.common.utils.TypeConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderProductRequestConverter {

    private static final Pattern ORDER_PRODUCT_REGEX = Pattern.compile("\\[([가-힣]+)-([0-9]+)\\]");

    public static PurchaseProductRequest toOrderProductRequest(String line) {
        Matcher matcher = ORDER_PRODUCT_REGEX.matcher(line);
        if (!matcher.matches()) {
            throw new ApplicationException(ErrorMessage.INVALID_ORDER_REQUEST_FORMAT);
        }
        String productName = matcher.group(1);
        int quantity = TypeConverter.toInteger(matcher.group(2));
        return new PurchaseProductRequest(productName, quantity);
    }
}
