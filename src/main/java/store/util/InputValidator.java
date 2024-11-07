package store.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    private static final String ORDER_REGEX = "\\[[가-힣]+-\\d+\\](,\\[[가-힣]+-\\d+\\])*";
    private static final String ORDER_COUNT_REGEX = "\\d+";
    private static final String ORDER_NAME_REGEX = "[가-힣]+";
    private static final String YES_NO_REGEX = "Y|N";

    public static void validateOrder(String input) {
        checkEmpty(input);
        checkOrderForm(input);
        checkDuplicated(input);
        checkZero(input);
    }

    private static void checkEmpty(String input) {
        if (input.isBlank()) {
            throw new IllegalArgumentException("[ERROR] 주문을 입력해주세요.");
        }
    }

    private static void checkOrderForm(String input) {
        if (!input.matches(ORDER_REGEX)) {
            throw new IllegalArgumentException("[ERROR] 잘못된 주문 형식입니다.");
        }
    }

    private static void checkDuplicated(String input) {
        Set<String> names = new HashSet<>();
        Pattern pattern = Pattern.compile(ORDER_NAME_REGEX);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            String name = matcher.group();
            if (!names.add(name)) {
                throw new IllegalArgumentException("[ERROR] 중복 물건을 주문 하셨습니다.");
            }
        }
    }

    private static void checkZero(String input) {
        Pattern pattern = Pattern.compile(ORDER_COUNT_REGEX);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            int count = Integer.parseInt(matcher.group());
            if (count == 0) {
                throw new IllegalArgumentException("[ERROR] 0개를 주문 할 수 없습니다.");
            }
        }
    }

    public static void validateYesOrNo(String input) {
        checkEmpty(input);
        checkYesOrNo(input);
    }

    private static void checkYesOrNo(String input) {
        if (!input.matches(YES_NO_REGEX)) {
            throw new IllegalArgumentException("[ERROR] Y 또는 N만 입력해주세요");
        }
    }
}
