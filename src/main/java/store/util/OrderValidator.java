package store.util;

import java.util.List;
import java.util.Map;
import java.util.Set;
import store.domain.Product;

public class OrderValidator {

    public static void validateOrderValues(Map<String, Integer> order, List<Product> products) {
        validateOrderNames(order, products);
        validateOrderCount(order, products);
    }

    private static void validateOrderNames(Map<String, Integer> order, List<Product> products) {
        Set<String> names = order.keySet();
        for (String name : names) {
            boolean hasProduct = products.stream()
                .anyMatch(product -> product.getName().equals(name));
            if (!hasProduct) {
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 물건을 주문하셨습니다");
            }
        }
    }

    private static void validateOrderCount(Map<String, Integer> order, List<Product> products) {
        Set<String> names = order.keySet();
        for (String name : names) {
            int sum = products.stream()
                .filter(product -> product.getName().equals(name))
                .mapToInt(product -> product.getQuantity())
                .sum();
            if (sum < order.get(name)) {
                throw new IllegalArgumentException("[ERROR] 재고 보다 많은 수량을 주문 하셨습니다.");
            }
        }
    }
}
