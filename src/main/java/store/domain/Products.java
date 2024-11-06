package store.domain;

import static store.util.NumberFormatter.formatNumber;

import java.util.List;
import store.util.FileConverter;

public class Products {

    private final List<Product> products;

    public Products(List<Product> products) {
        this.products = products;
    }

}
