package store.reader;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StoreFileReaderTest {

    @Test
    void product() {
        StoreFileReader storeFileReader = new StoreFileReader();
        storeFileReader.product();

    }

    @Test
    void promotions() {
        StoreFileReader storeFileReader = new StoreFileReader();
        storeFileReader.promotions();
    }
}