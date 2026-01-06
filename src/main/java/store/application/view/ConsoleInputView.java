package store.application.view;

import camp.nextstep.edu.missionutils.Console;
import store.common.utils.TypeConverter;

public class ConsoleInputView {

    public boolean readTF() {
        return TypeConverter.toBoolean(Console.readLine());
    }

    public String readLine() {
        return Console.readLine();
    }
}
