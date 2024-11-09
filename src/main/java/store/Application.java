package store;

import store.config.ConvenienceFactory;
import store.convenience.Convenience;

public class Application {
    public static void main(String[] args) {
        Convenience convenience = ConvenienceFactory.createConvenience();
        convenience.operate();
    }
}
