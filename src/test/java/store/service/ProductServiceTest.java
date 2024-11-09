package store.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import store.domain.Product;
import store.domain.Promotion;
import store.domain.ShortageQuantity;
import store.domain.request.PurchaseRequest;
import store.reader.StoreFileReader;
import store.repository.ProductRepository;

class ProductServiceTest {

    @Test
    void getShortageQuantityForPromotion() {
        StoreFileReader storeFileReader = new StoreFileReader();
        Map<String, Promotion> promotions = storeFileReader.promotions();
        Map<String, Product> product = storeFileReader.product(promotions);
        ProductService productService = new ProductService(new ProductRepository(product));

        List<PurchaseRequest> purchaseRequests = new ArrayList<>();
        purchaseRequests.add(new PurchaseRequest("콜라", 13));

        List<ShortageQuantity> shortageQuantityForPromotion = productService.getShortageQuantityForPromotion(
                purchaseRequests);
        for (ShortageQuantity shortageQuantity : shortageQuantityForPromotion) {
            System.out.println(shortageQuantity);
        }
    }

}