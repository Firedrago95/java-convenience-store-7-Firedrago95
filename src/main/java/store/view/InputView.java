package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;
import store.util.InputValidator;
import store.util.Parser;

public class InputView {

    public static final String ORDER_INPUT_MESSAGE
        = "구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])";
    public static final String ADD_COUNT_MESSAGE
        = "현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)%n";
    public static final String SUBSTRACT_COUNT_MESSAGE
        = "현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)%n";

    public static Map<String, Integer> readOrder() {
        System.out.println(ORDER_INPUT_MESSAGE);
        String input = Console.readLine();
        InputValidator.validateOrder(input);
        return Parser.parseToMap(input);
    }

    public static boolean readAddCount(String lessCountOrder) {
        System.out.printf(ADD_COUNT_MESSAGE, lessCountOrder);
        return checkYesOrNo();
    }

    public static boolean readSubStractCount(String name, Integer count) {
        System.out.printf(SUBSTRACT_COUNT_MESSAGE, name, count);
        return checkYesOrNo();
    }

    private static boolean checkYesOrNo() {
        String input = Console.readLine();
        InputValidator.validateYesOrNo(input);
        return isYesResponse(input);
    }

    private static boolean isYesResponse(String input) {
        if (input.equals("Y")) {
            return true;
        }
        return false;
    }
}
