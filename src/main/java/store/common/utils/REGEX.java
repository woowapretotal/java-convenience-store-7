package store.common.utils;

import java.util.regex.Pattern;

public class REGEX {
    //XYZ 중 하나를 한 글자만 입력받는 정규식
    private static final Pattern ONE_CHAR_REGEX = Pattern.compile("[XYZ]");

    // "[한글]" 형식의 문자열을 입력받는 정규식
    private static final Pattern KOREAN_WITH_BRACKET_REGEX = Pattern.compile("\\[[가-힣]+\\]");

    // "[한글-숫자]" 형식의 문자열을 입력받는 정규식
    private static final Pattern KOREAN_WITH_BRACKET_AND_NUMERIC_REGEX = Pattern.compile("\\[[가-힣]+-[0-9]+\\]");

    //참고용 정규식들
    private static final Pattern NUMERIC_REGEX = Pattern.compile("[0-9]+");
    private static final Pattern KOREAN_REGEX = Pattern.compile("[가-힣]+");
    private static final Pattern ENGLISH_REGEX = Pattern.compile("[a-zA-Z]+");
    private static final Pattern ALPHANUMERIC_REGEX = Pattern.compile("[a-zA-Z0-9]+");
    private static final Pattern ALPHANUMERIC_WITH_SPACE_REGEX = Pattern.compile("[a-zA-Z0-9 ]+");

    /*private static void validateIsNumeric(String input) {
        if (!NUMBER_PATTERN.matcher(input).matches()) {
            throw new IllegalArgumentException(INVALID_NOT_NUMERIC.getMessage());
        }
    }*/
}

