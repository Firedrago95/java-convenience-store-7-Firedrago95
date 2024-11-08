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
                throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
            }
        }
    }

    private static void validateOrderCount(Map<String, Integer> order, List<Product> products) {
        Set<String> names = order.keySet();
        for (String name : names) {
            int sum = getAllQuantity(products, name);
            if (sum < order.get(name)) {
                throw new IllegalArgumentException(
                    "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
            }
        }
    }

    private static int getAllQuantity(List<Product> products, String name) {
        return products.stream()
            .filter(product -> product.getName().equals(name))
            .mapToInt(product -> product.getQuantity())
            .sum();
    }
}
