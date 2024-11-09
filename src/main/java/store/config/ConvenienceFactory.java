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

    public static ConvenienceController convenienceController() {
        return new ConvenienceController(convenienceService());
    }

    public static ConvenienceService convenienceService() {
        return new ConvenienceService(productService(), promotionService());
    }

    public static OutputView outputView() {
        return new OutputView();
    }

    public static InputView inputView() {
        return new InputView();
    }

    public static StoreFileReader storeFileReader() {
        return new StoreFileReader();
    }

    public static ProductRepository productRepository() {
        Map<String, Promotion> promotions = storeFileReader().promotions();
        Map<String, Product> products = storeFileReader().product(promotions);
        return new ProductRepository(products);
    }

    public static ProductService productService() {
        return new ProductService(productRepository());
    }

    public static PromotionService promotionService() {
        return new PromotionService(promotionRepository());
    }

    public static PromotionRepository promotionRepository() {
        return new PromotionRepository(storeFileReader().promotions());
    }
}
