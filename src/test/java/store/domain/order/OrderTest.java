package store.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.domain.Order;
import store.domain.Product;
import store.domain.Promotion;

class OrderTest {

    @Test
    public void 주문수량_감소_테스트() {
        // given
        Order order = createOrder(createOrderMap(2, 3));
        Map<String, Integer> decreaseOrder = Map.of("상품1", 1, "상품2", 2);

        // when
        order.substractCount(decreaseOrder);

        // then
        Map<String, Integer> expected = Map.of("상품1", 1, "상품2", 1);
        assertEquals(expected, order.getOrder());
    }

    @Test
    public void 주문수량_증가_테스트() {
        // given
        Order order = createOrder(createOrderMap(5, 3));
        List<String> increaseOrder = Arrays.asList("상품1", "상품2");

        // when
        order.addCount(increaseOrder);

        // then
        Map<String, Integer> expected = Map.of("상품1", 6, "상품2", 4);
        assertEquals(expected, order.getOrder());
    }

    private Order createOrder(Map<String, Integer> orderMap) {
        LocalDateTime now = DateTimes.now();
        List<Product> products = Arrays.asList(
            new Product("상품1", 10, 5,
                new Promotion("반짝할인", 1, 1, now.minusDays(1), now.plusDays(1))),
            new Product("상품2", 2, 10,
                new Promotion("반짝할인", 1, 1, now.minusDays(1), now.plusDays(1)))
        );
        return new Order(orderMap, products);
    }

    private Map<String, Integer> createOrderMap(int quantity1, int quantity2) {
        Map<String, Integer> orderMap = new HashMap<>();
        orderMap.put("상품1", quantity1);
        orderMap.put("상품2", quantity2);
        return orderMap;
    }
}