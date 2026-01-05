package store.application.view;

import java.text.NumberFormat;

public class MoneyFormatter {
    private static final String MONEY_UNIT = "원";
    private static final String MINUS = "-";
    private static final NumberFormat numberFormat = NumberFormat.getInstance();
    
    public static String formatInteger(int money) {
        return numberFormat.format(money) + MONEY_UNIT;
    }

    public static String minusFormat(int money) {
        if (money == 0) {
            return formatInteger(money);
        }
        return MINUS + formatInteger(money);
    }
}
