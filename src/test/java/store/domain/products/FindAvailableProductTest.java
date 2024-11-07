package store.domain.products;

import camp.nextstep.edu.missionutils.DateTimes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;

import static org.junit.jupiter.api.Assertions.*;

public class FindAvailableProductTest {

    private Products products;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        now = DateTimes.now();
        Product product1 = new Product("콜라", 1000, 10,
            new Promotion("탄산2+1", 2, 1, now.minusDays(1), now.plusDays(1)));
        Product product2 = new Product("오렌지주스", 2000, 5,
            new Promotion("MD추천상품", 1, 1, now.minusDays(1), now.plusDays(1)));
        Product product3 = new Product("탄산수", 1500, 0,
            new Promotion("탄산2+1", 2, 1, now.minusDays(2), now.minusDays(1)));
        Product product4 = new Product("물", 3000, 0, null);

        products = new Products(List.of(product1, product2, product3, product4));
    }

    @Test
    void 기간에_맞는_프로모션_확인_검증() {
        // given
        Set<String> orderNames = Set.of("콜라", "오렌지주스", "탄산수", "물");

        // when
        List<Product> availableProducts = products.findAvailableProduct(orderNames, now);

        // then
        assertEquals(2, availableProducts.size());
        assertTrue(availableProducts.stream().anyMatch(p -> p.getName().equals("콜라")));
        assertTrue(availableProducts.stream().anyMatch(p -> p.getName().equals("오렌지주스")));
        assertFalse(availableProducts.stream().anyMatch(p -> p.getName().equals("탄산수")));
        assertFalse(availableProducts.stream().anyMatch(p -> p.getName().equals("물")));
    }

    @Test
    void 프로모션_없는_제품_제외_검증() {
        // given
        Set<String> orderNames = Set.of("물");

        // when
        List<Product> availableProducts = products.findAvailableProduct(orderNames, now);

        // then
        assertTrue(availableProducts.isEmpty());
    }

    @Test
    void 프로모션_지난_날짜_확인_검증() {
        // given
        Set<String> orderNames = Set.of("탄산수");

        // when
        List<Product> availableProducts = products.findAvailableProduct(orderNames, now);

        // then
        assertTrue(availableProducts.isEmpty());
    }
}

