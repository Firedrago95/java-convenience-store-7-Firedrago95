package store.service;

import java.util.List;
import store.domain.Product;
import store.domain.Products;
import store.util.FileConverter;

public class StoreService {

    private Products products;

    public void createProducts() {
        FileConverter.readPromotionsFile();
        List<Product> convertedProducts = FileConverter.readProductsFile();
        products = new Products(convertedProducts);
    }

    public String makeProductsStatus() {
        return products.toString();
    }
}
