package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void toString_출력_형식_검증() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Promotion promotion = new Promotion("반짝할인", 1, 1, now, now);
        Product product = new Product("물", 1000, 10, promotion);
        String expectedResult = String.format("- 물 1,000원 10개 반짝할인%n");

        //when
        String result = product.toString();

        //then
        assertEquals(result, expectedResult);
    }

    @Test
    void 프로모션_확인_검증() {
        //given
        LocalDateTime now = LocalDateTime.now();
        Promotion promotion = new Promotion("반짝할인", 1, 1, now, now);
        Product product1 = new Product("물", 1000, 10, promotion);
        Product product2 = new Product("물", 1000, 10, null);

        //when
        boolean hasPromotion = product1.hasPromotion();
        boolean notPromotion = product2.hasPromotion();

        //then
        assertTrue(hasPromotion);
        assertFalse(notPromotion);
    }

    @Test
    void 재고보다_적은_수량_감소_테스트() {
        // given
        Product product = new Product("콜라", 1000, 10, null);

        // when
        product.decreaseQuantity(9);

        // then
        assertEquals(product.getQuantity(), 1);
    }

    @Test
    void 재고보다_많은_수량_감소_테스트() {
        // given
        Product product = new Product("콜라", 1000, 10, null);

        // when
        product.decreaseQuantity(11);

        // then
        assertEquals(product.getQuantity(), 0);
    }
}