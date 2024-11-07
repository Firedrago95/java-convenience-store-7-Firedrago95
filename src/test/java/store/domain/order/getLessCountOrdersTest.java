package store.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Order;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;

public class getLessCountOrdersTest {
    private List<Product> products;
    private Order order;

    @BeforeEach
    void setUp() {
        LocalDateTime now = DateTimes.now();
        Product productA = new Product("콜라", 100, 20,
            new Promotion("탄산2+1", 2, 1, now.minusDays(1), now.plusDays(1)));
        Product productB = new Product("탄산수", 200, 10,
            new Promotion("탄산2+1", 2, 1, now.minusDays(1), now.plusDays(1)));
        Product productC = new Product("초코바", 150, 5,
            new Promotion("MD추천상품", 1, 1, now.minusDays(1), now.plusDays(1)));

        products = Arrays.asList(productA, productB, productC);
    }

    @Test
    void 주문_프로모션_조건_충족하는_상품_찾기() {
        // given
        Map<String, Integer> orderMap = new HashMap<>();
        orderMap.put("콜라", 19);
        orderMap.put("탄산수", 2);
        orderMap.put("초코바", 1);

        order = new Order(orderMap, products);

        // when
        List<String> lessCountOrders = order.getLessCountOrders(products);

        // then
        assertEquals(2, lessCountOrders.size());
        assertTrue(lessCountOrders.contains("탄산수"));
        assertTrue(lessCountOrders.contains("초코바"));
    }

}
