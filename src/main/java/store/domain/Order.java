package store.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import store.util.OrderValidator;

public class Order {

    private final Map<String, Integer> order;

    public Order(Map<String, Integer> order, List<Product> products) {
        OrderValidator.validateOrderValues(order, products);
        this.order = order;
    }

    public Set<String> getOrderNames() {
        return order.keySet();
    }

    public List<String> getLessCountOrders(List<Product> availableProduct) {
        List<String> lessCount = new ArrayList<>();
        for (String orderName : getOrderNames()) {
            makeLessCount(availableProduct, lessCount, orderName);
        }
        return lessCount;
    }

    private void makeLessCount(List<Product> products, List<String> lessCount, String orderName) {
        int count = order.get(orderName);
        boolean isEligibleForPromotion = products.stream()
            .anyMatch(product -> product.getName().equals(orderName)
                && count + 1 <= product.getQuantity()
                && count % (product.getPromotionBuy() + product.getPromotionGet())
                    == product.getPromotionBuy());
        if (isEligibleForPromotion) {
            lessCount.add(orderName);
        }
    }

    public void addCount(List<String> filteredLessCountOrders) {
        filteredLessCountOrders.stream()
            .forEach(f ->{
                order.put(f, order.get(f) + 1);
            });
    }

    public Map<String, Integer> getExceedCountOrders(List<Product> products) {
        Map<String, Integer> exceedCount = new HashMap<>();
        for (String orderName : getOrderNames()) {
            int count = order.get(orderName);
            boolean isEligible = checkExceedCount(products, orderName, count);
            if (isEligible) {
                calculateExceedCount(products, exceedCount, orderName, count);
            }
        }
        return exceedCount;
    }

    private static boolean checkExceedCount(List<Product> products, String orderName, int count) {
        boolean isEligible = products.stream()
            .anyMatch(product -> product.getName().equals(orderName)
                && count > product.getQuantity());
        return isEligible;
    }

    private static void calculateExceedCount(List<Product> products, Map<String, Integer> exceedCount,
        String orderName, int count) {
        products.stream()
            .filter(product -> product.getName().equals(orderName))
            .forEach(product -> {
                int promotionUnit = product.getPromotionBuy() + product.getPromotionGet();
                int eligibleForPromotion = product.getQuantity() - (product.getQuantity() % promotionUnit);
                int calculatedCount = count - eligibleForPromotion;
                exceedCount.put(orderName, calculatedCount);
            });
    }

    public void substractCount(Map<String, Integer> filteredOrders) {
        filteredOrders.forEach((name, count) -> {
            order.put(name, order.get(name) - count);
        });
    }

    public Map<String, Integer> getOrder() {
        return order;
    }
}
