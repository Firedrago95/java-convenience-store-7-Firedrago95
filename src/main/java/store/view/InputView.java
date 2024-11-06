package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import store.util.InputValidator;
import store.util.Parser;

public class InputView {

    public static final String ORDER_INPUT_MESSAGE
        = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";

    public static Map<String, Integer> readOrder() {
        System.out.println(ORDER_INPUT_MESSAGE);
        String input = Console.readLine();
        InputValidator.validateOrder(input);
        return Parser.parseToMap(input);
    }
}
