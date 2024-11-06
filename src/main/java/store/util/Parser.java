package store.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static final String REGEX = "([가-힣]+)-(\\d+)";

    public static Map<String, Integer> parseToMap(String input) {
        Map<String, Integer> order = new HashMap<>();
        Matcher matcher = makeMatcher(input);
        while (matcher.find()) {
            makeOrder(order, matcher);
        }
        return order;
    }

    private static Matcher makeMatcher(String input) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(input);
        return matcher;
    }

    private static void makeOrder(Map<String, Integer> order, Matcher matcher) {
        String name = matcher.group(1);
        int count = Integer.parseInt(matcher.group(2));
        order.put(name, count);
    }
}
