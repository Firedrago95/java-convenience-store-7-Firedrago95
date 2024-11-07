package store.domain.products;

import static org.junit.jupiter.api.Assertions.assertEquals;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;

public class ChangeQuantityTest {

    private Products products;
    private Product promoCoke;
    private Product regularCoke;
    private Product regularWater;

    @BeforeEach
    public void setUp() {
        LocalDateTime now = DateTimes.now();
        Promotion promotion = new Promotion("반짝할인", 1, 1,
            now.minusDays(1), now.plusDays(1));
        promoCoke = new Product("콜라", 1000, 5, promotion);
        regularCoke = new Product("콜라", 1000, 10, null);
        regularWater = new Product("물", 2000, 10, null);

        List<Product> productList = Arrays.asList(promoCoke, regularCoke, regularWater);
        products = new Products(productList);
    }

    @Test
    public void 단일_상품_감소_테스트() {
        // given
        Map<String, Integer> order = new HashMap<>();
        order.put("물", 3);

        // when
        products.changeQuantity(order);

        // then
        assertEquals(5, promoCoke.getQuantity());
        assertEquals(10, regularCoke.getQuantity());
        assertEquals(7, regularWater.getQuantity());
    }

    @Test
    public void 두개_상품_감소_테스트() {
        // given
        Map<String, Integer> order = new HashMap<>();
        order.put("콜라", 7);

        // when
        products.changeQuantity(order);

        // then
        assertEquals(0, promoCoke.getQuantity());
        assertEquals(8, regularCoke.getQuantity());
        assertEquals(10, regularWater.getQuantity());
    }

    @Test
    public void 모든_상품_감소_테스트() {
        // given
        Map<String, Integer> order = new HashMap<>();
        order.put("콜라", 12);
        order.put("물", 8);

        // when
        products.changeQuantity(order);

        //then
        assertEquals(0, promoCoke.getQuantity());
        assertEquals(3, regularCoke.getQuantity());
        assertEquals(2, regularWater.getQuantity());
    }
}
