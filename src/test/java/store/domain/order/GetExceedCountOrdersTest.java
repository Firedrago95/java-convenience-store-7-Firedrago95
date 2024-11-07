package store.domain.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Order;
import store.domain.Product;
import store.domain.Promotion;

public class GetExceedCountOrdersTest {

    private List<Product> createProducts;
    private List<Product> testProducts;
    private Order order;

    @BeforeEach
    public void setUp() {
        LocalDateTime now = DateTimes.now();
        Product cokePromotion = new Product("콜라", 100, 7,
            new Promotion("탄산2+1", 2, 1, now.minusDays(1), now.plusDays(1)));
        Product sWaterPromotion = new Product("탄산수", 200, 11,
            new Promotion("탄산2+1", 2, 1, now.minusDays(1), now.plusDays(1)));
        Product chocoPromotion = new Product("초코바", 150, 5,
            new Promotion("MD추천상품", 1, 1, now.minusDays(1), now.plusDays(1)));
        Product cokeNonPromotion = new Product("콜라", 100, 10, null);
        Product sWaterNonPromotion = new Product("탄산수", 200, 10, null);
        Product chocoNonPromotion = new Product("초코바", 150, 5, null);
        createProducts = Arrays.asList(cokePromotion, cokeNonPromotion, sWaterPromotion,
            sWaterNonPromotion, chocoPromotion, chocoNonPromotion);
        testProducts = Arrays.asList(cokePromotion, sWaterPromotion, chocoPromotion);
    }

    @Test
    public void 주문_수량이_초과된_상품이_있을_경우_정확한_초과수량을_반환한다() {
        // given
        Map<String, Integer> orderMap = new HashMap<>();
        orderMap.put("콜라", 10);
        orderMap.put("탄산수", 12);
        orderMap.put("초코바", 4);
        order = new Order(orderMap, createProducts);

        // when
        Map<String, Integer> exceedCount = order.getExceedCountOrders(testProducts);

        // then
        assertEquals(2, exceedCount.size());
        assertEquals(4, exceedCount.get("콜라"));
        assertEquals(3, exceedCount.get("탄산수"));
        assertTrue(!exceedCount.containsKey("초코바"));
    }

    @Test
    public void 모든_상품이_주문수량을_초과하지_않은_경우_빈_맵을_반환한다() {
        // given
        Map<String, Integer> orderMap = new HashMap<>();
        orderMap.put("콜라", 6);
        orderMap.put("탄산수", 10);
        orderMap.put("초코바", 4);
        order = new Order(orderMap, createProducts);

        // when
        Map<String, Integer> exceedCount = order.getExceedCountOrders(testProducts);

        // then
        assertTrue(exceedCount.isEmpty());
    }

    @Test
    public void 주문수량이_프로모션재고와_같을_경우_초과수량을_반환하지_않는다() {
        // given
        Map<String, Integer> orderMap = new HashMap<>();
        orderMap.put("콜라", 16);
        orderMap.put("탄산수", 11);
        order = new Order(orderMap, createProducts);

        // when
        Map<String, Integer> exceedCount = order.getExceedCountOrders(testProducts);

        // then
        assertEquals(10, exceedCount.get("콜라"));
        assertTrue(!exceedCount.containsKey("탄산수"));
    }
}
