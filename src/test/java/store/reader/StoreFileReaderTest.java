package store.reader;

import java.util.Map;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;

class StoreFileReaderTest {

    @Test
    void product() {
        StoreFileReader storeFileReader = new StoreFileReader();
        Map<String, Promotion> promotions = storeFileReader.promotions();
        Map<String, Product> product = storeFileReader.product(promotions);
        System.out.println(product);

    }

    @Test
    void promotions() {
        StoreFileReader storeFileReader = new StoreFileReader();
        Map<String, Promotion> promotions1 = storeFileReader.promotions();
        System.out.println(promotions1);
    }
}