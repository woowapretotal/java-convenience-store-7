package store.common.utils;

import store.common.error.ApplicationException;

import java.util.Arrays;
import java.util.List;

import static store.common.error.ErrorMessage.EMPTY_CSV_VALUE;

public final class CSVParser {
    private static final String COMMA = ",";

    private CSVParser() {
    }

    public static List<String> split(final String commaSeperatedValue) {
        validateNotBlank(commaSeperatedValue);
        return Arrays.stream(commaSeperatedValue.split(COMMA))
                .map(String::trim)
                .toList();
    }

    private static void validateNotBlank(final String commaSeperatedValue) {
        if (commaSeperatedValue == null || commaSeperatedValue.isBlank()) {
            throw new ApplicationException(EMPTY_CSV_VALUE);
        }
    }
}
