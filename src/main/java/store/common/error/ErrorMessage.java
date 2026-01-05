package store.common.error;

public enum ErrorMessage {
    INVALID_INTEGER_FORMAT("정수 형식의 문자가 아닙니다."),
    INVALID_DECIMAL_FORMAT("소수 형식의 문자가 아닙니다."),
    INVALID_DATE_FORMAT("올바른 날짜 형식의 문자 입력이 아닙니다."),
    EMPTY_INPUT("입력이 비어있을 수 없습니다."),
    BLANK_STRING("문자열이 비어있거나 공백일 수 없습니다."),
    EXCEEDS_MAX_VALUE("최댓값 %s를 초과했습니다."),
    BELOW_MIN_VALUE("최솟값 %s 미만입니다."),
    EXCEEDS_MAX_LENGTH("최대 길이 %d를 초과하였습니다."),
    BELOW_MIN_LENGTH("최소 길이 %d 미만입니다."),
    INVALID_PRODUCT_PROMOTION("상품 데이터에 저장된 프로모션 정보가 존재하지 않습니다."),
    NOT_EXISTS_PRODUCT("상품 데이터에 해당 상품이 존재하지 않습니다."),

    INVALID_MENU_NUMBER("존재하지 않는 메뉴를 선택하셨습니다."),
    EMPTY_CSV_VALUE("비어있는 항목이 존재합니다.");

    private final String message;

    ErrorMessage(final String message) {
        this.message = message;
    }

    public String message() {
        return message;
    }

    public String formatted(final Object... args) {
        return message.formatted(args);
    }
}
