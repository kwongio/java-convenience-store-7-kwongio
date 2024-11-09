package store.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import store.domain.Product;
import store.domain.Promotion;

public class StoreFileReader {

    public static final String PRODUCTS_DATA = "src/main/resources/products.md";
    public static final String PROMOTIONS_DATA = "src/main/resources/promotions.md";

    public Map<String, Product> product(Map<String, Promotion> promotions) {
        Map<String, Product> products = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(PRODUCTS_DATA)))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] product = line.split(",");
                String productName = product[0];
                if (products.containsKey(productName)) { // 두번째로 들어오는 건 무조건 일반 재고
                    Product p = products.get(productName);
                    int quantity = Integer.parseInt(product[2]);
                    p.add(quantity);
                }
                products.computeIfAbsent(productName, (k) -> convertProduct(product, promotions));

            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file.", e);
        }
        return products;
    }

    private Product convertProduct(String[] product, Map<String, Promotion> promotions) {
        String name = product[0];
        int price = Integer.parseInt(product[1]);
        int quantity = Integer.parseInt(product[2]);
        String promotion = product[3];
        if (isPromotion(product)) {
            return new Product(name, price, quantity, promotions.get(promotion));
        }
        return new Product(name, price, quantity);
    }

    private static boolean isPromotion(String[] product) {
        return !product[3].equals("null");
    }

    public Map<String, Promotion> promotions() {
        Map<String, Promotion> promotions = new LinkedHashMap<>();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(PROMOTIONS_DATA)))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] promotion = line.split(",");
                String promotionName = promotion[0];
                promotions.put(promotionName, convertPromotion(promotion));
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file.", e);
        }
        return promotions;
    }

    private Promotion convertPromotion(String[] promotion) {
        return new Promotion(promotion[0], Integer.parseInt(promotion[1]), Integer.parseInt(promotion[2]),
                LocalDate.parse(promotion[3]), LocalDate.parse(promotion[4]));
    }
}
