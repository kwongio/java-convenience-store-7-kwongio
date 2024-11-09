package store.view;

import java.util.NoSuchElementException;
import java.util.function.Supplier;

public class RetryOnInvalidInput {

    public static <T> T retryOnException(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return supplier.get();
            }
        }
    }
}
