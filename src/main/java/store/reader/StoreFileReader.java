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

    public static Map<String, Product> product(Map<String, Promotion> promotions) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(PRODUCTS_DATA)))) {
            return getProductMap(promotions, br);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file.", e);
        }
    }

    private static Map<String, Product> getProductMap(Map<String, Promotion> promotions, BufferedReader br)
            throws IOException {
        Map<String, Product> products = new LinkedHashMap<>();
        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            String[] product = line.split(",");
            String productName = product[0];
            addProduct(products, productName, product);
            products.computeIfAbsent(productName, (k) -> convertProduct(product, promotions));
        }
        return products;
    }

    private static void addProduct(Map<String, Product> products, String productName, String[] product) {
        if (products.containsKey(productName)) {
            Product p = products.get(productName);
            p.add(Integer.parseInt(product[2]));
        }
    }

    private static Product convertProduct(String[] product, Map<String, Promotion> promotions) {
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

    public static Map<String, Promotion> promotions() {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(PROMOTIONS_DATA)))) {
            return getPromotionMap(br);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file.", e);
        }

    }

    private static Map<String, Promotion> getPromotionMap(BufferedReader br) throws IOException {
        Map<String, Promotion> promotions = new LinkedHashMap<>();
        String line = br.readLine();
        while ((line = br.readLine()) != null) {
            String[] promotion = line.split(",");
            String promotionName = promotion[0];
            promotions.put(promotionName, convertPromotion(promotion));
        }
        return promotions;
    }

    private static Promotion convertPromotion(String[] promotion) {
        return new Promotion(promotion[0], Integer.parseInt(promotion[1]), Integer.parseInt(promotion[2]),
                LocalDate.parse(promotion[3]), LocalDate.parse(promotion[4]));
    }
}
