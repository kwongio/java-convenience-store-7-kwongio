package store.config;

import java.util.Map;
import store.controller.ConvenienceController;
import store.convenience.Convenience;
import store.domain.Product;
import store.domain.Promotion;
import store.reader.StoreFileReader;
import store.repository.ProductRepository;
import store.repository.PromotionRepository;
import store.service.ConvenienceService;
import store.service.ProductService;
import store.service.PromotionService;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceFactory {

    public static Convenience createConvenience() {
        return new Convenience(convenienceController(), outputView(), inputView());
    }

    private static ConvenienceController convenienceController() {
        return new ConvenienceController(convenienceService());
    }

    public static ConvenienceService convenienceService() {
        return new ConvenienceService(productService(), promotionService());
    }

    private static OutputView outputView() {
        return new OutputView();
    }

    private static InputView inputView() {
        return new InputView();
    }

    private static StoreFileReader storeFileReader() {
        return new StoreFileReader();
    }

    private static ProductRepository productRepository() {
        Map<String, Promotion> promotions = storeFileReader().promotions();
        Map<String, Product> products = storeFileReader().product(promotions);
        return new ProductRepository(products);
    }

    private static ProductService productService() {
        return new ProductService(productRepository());
    }

    private static PromotionService promotionService() {
        return new PromotionService(promotionRepository());
    }

    private static PromotionRepository promotionRepository() {
        return new PromotionRepository(storeFileReader().promotions());
    }
}
