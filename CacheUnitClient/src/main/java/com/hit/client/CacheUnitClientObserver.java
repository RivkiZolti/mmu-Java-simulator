package main.java.com.hit.client;

import main.java.com.hit.view.CacheUnitView;

import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 *  Called by CacheUnitView with user request,
 *  opens the request sends it to CacheUnitClient receives a reply ת
 *  and returns text to display to the user.
 */
public class CacheUnitClientObserver
        extends Object
        implements PropertyChangeListener {
    private CacheUnitClient cacheUnitClient;

    public CacheUnitClientObserver() {
        cacheUnitClient = new CacheUnitClient();
    }

    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        CacheUnitView view = (CacheUnitView) evt.getSource();
        String json = (String) evt.getNewValue();
        try {
            String result = cacheUnitClient.send(json);
            view.updateUIData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
