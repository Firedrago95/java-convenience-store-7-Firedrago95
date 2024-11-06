package store.util;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;

class OrderValidatorTest {

    @Test
    void 주문_검증_예외_테스트() {
        // given
        LocalDateTime now = LocalDateTime.now();
        List<Product> products = createProductList(now);

        // when & then
        assertOrderValidationThrowsException(
            new HashMap<String, Integer>() {{put("식탁보", 1);}}, products);
        assertOrderValidationThrowsException(
            new HashMap<String, Integer>() {{put("물", 6);}}, products);
    }

    private void assertOrderValidationThrowsException(Map<String, Integer> order,
        List<Product> products) {
        assertThrows(IllegalArgumentException.class,
            () -> OrderValidator.validateOrderValues(order, products));
    }

    private List<Product> createProductList(LocalDateTime now) {
        return List.of(
            new Product("콜라", 1000, 10, null),
            new Product("물", 1000, 2, null),
            new Product("물", 1000, 3,
                new Promotion("특별할인", 1, 1, now, now))
        );
    }
}