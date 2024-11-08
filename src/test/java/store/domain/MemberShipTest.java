package store.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MemberShipTest {

    @Test
    void 멤버십_할인_테스트() {
        // given
        int price1 = 4000;
        int price2 = 50000;

        // when
        int result1 = MemberShip.DISCOUNT.applyDiscount(price1);
        int result2 = MemberShip.DISCOUNT.applyDiscount(price2);

        // then
        assertEquals(result1, 1200);
        assertEquals(result2, 8000);
    }

}