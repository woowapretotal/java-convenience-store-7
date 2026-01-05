package store.application.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.utils.TypeConverter;

public class ConsoleInputView {

    public int readNumber() {
        return TypeConverter.toInteger(readLine());
    }

    public String readLine() {
        return Console.readLine();
    }
}
