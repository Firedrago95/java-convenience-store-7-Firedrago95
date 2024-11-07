package store.domain.products;

import static org.assertj.core.api.Assertions.assertThat;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Products;
import store.domain.Promotion;

class ProductsTest {

    @Test
    void 상품_출력_형식_검증() {
        // given
        LocalDateTime now = DateTimes.now();
        Promotion promotion = new Promotion("반짝할인", 1, 1, now, LocalDateTime.now());
        List<Product> testProducts = Arrays.asList(
            new Product("물", 1000, 10, promotion)
        );
        Products products = new Products(testProducts);

        // when
        String result = products.toString();

        // then
        assertThat(result).contains(
            "- 물 1,000원 10개 반짝할인",
            "- 물 1,000원 재고 없음");
    }
}