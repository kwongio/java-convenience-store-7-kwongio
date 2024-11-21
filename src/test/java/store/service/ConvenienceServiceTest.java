package store.service;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.config.ConvenienceFactory;
import store.domain.Receipt;
import store.domain.request.PurchaseRequest;

class ConvenienceServiceTest {

    @Test
    void sell() {
        ConvenienceService convenienceService = ConvenienceFactory.convenienceService();

        List<PurchaseRequest> purchaseRequests = new ArrayList<>();
        purchaseRequests.add(new PurchaseRequest("콜라", 13));
        Receipt sell = convenienceService.sell(purchaseRequests, "Y");

        System.out.println(sell);


    }
}