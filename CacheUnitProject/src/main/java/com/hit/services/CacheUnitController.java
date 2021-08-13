package main.java.com.hit.services;

import main.java.com.hit.dm.DataModel;

public class CacheUnitController<T> extends Object {
    private final CacheUnitService<T> cacheUnitService;

    /**
     * Its purpose is to create a separation layer between the CacheUnitService and the layer
     */
    public CacheUnitController() {
        cacheUnitService = new CacheUnitService<T>();
    }

    public String getStatistics() {
        return cacheUnitService.getCapacity() + ";" + cacheUnitService.getAlgorithm() + ";" + cacheUnitService.getTotalNumberOfRequests() + ";" + cacheUnitService.getTotalNumberOfDM() + ";" + cacheUnitService.getTotalNumberSwaps()+";";
    }

    public boolean update(DataModel<T>[] dataModels) {
        return cacheUnitService.update(dataModels);
    }

    public boolean delete(DataModel<T>[] dataModels) {
        return cacheUnitService.delete(dataModels);
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        return cacheUnitService.get(dataModels);
    }

    public void saveOnShutdown() {
        cacheUnitService.saveOnShutdown();
    }


}
