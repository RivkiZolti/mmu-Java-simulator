package main.java.com.hit.driver;

import main.java.com.hit.client.CacheUnitClientObserver;
import main.java.com.hit.view.CacheUnitView;

public class CacheUnitClientDriver extends Object {
    public CacheUnitClientDriver() {
    }

    public static void main(String[] args) {
        CacheUnitClientObserver cacheUnitClientObserver =
                new CacheUnitClientObserver();
        CacheUnitView view = new CacheUnitView();
        view.addPropertyChangeListener(cacheUnitClientObserver);
        view.start();
    }
}
