package store.reader;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StoreFileReader {

    public static final String PRODUCTS = "src/main/resources/products.md";
    public static final String PROMOTIONS = "src/main/resources/promotions.md";

    public void product() {
        read(PRODUCTS);
    }

    public void promotions() {
        read(PROMOTIONS);
    }

    private static void read(String path) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read the file.", e);
        }
    }
}
