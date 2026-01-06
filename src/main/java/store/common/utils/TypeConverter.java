package store.common.utils;

import store.common.error.ApplicationException;
import store.common.error.ErrorMessage;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TypeConverter {

    public static int toInteger(String line) {
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            throw new ApplicationException(ErrorMessage.INVALID_INTEGER_FORMAT);
        }
    }

    public static boolean toBoolean(String line) {
        if (line.equals("Y")) {
            return true;
        }
        if (line.equals("N")) {
            return false;
        }

        throw new ApplicationException(ErrorMessage.INVALID_Y_N_FORMAT);
    }

    public static double toDecimal(String line) {
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            throw new ApplicationException(ErrorMessage.INVALID_DECIMAL_FORMAT);
        } catch (NullPointerException e) {
            throw new ApplicationException(ErrorMessage.EMPTY_INPUT);
        }
    }

    public static LocalDateTime toLocalDateTime(String line) {
        try {
            return LocalDateTime.parse(line, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } catch (DateTimeException e) {
            throw new ApplicationException(ErrorMessage.INVALID_DATE_FORMAT);
        } catch (NullPointerException e) {
            throw new ApplicationException(ErrorMessage.EMPTY_INPUT);
        }
    }

    public static LocalDate toLocalDate(String line) {
        try {
            return LocalDate.parse(line);
        } catch (DateTimeException e) {
            throw new ApplicationException(ErrorMessage.INVALID_DATE_FORMAT);
        } catch (NullPointerException e) {
            throw new ApplicationException(ErrorMessage.EMPTY_INPUT);
        }
    }
}
