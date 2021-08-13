package main.java.com.hit.services;

import com.mbj.algorithm.LRUAlgoCacheImpl;
import main.java.com.hit.dao.DaoFileImpl;
import main.java.com.hit.dao.IDao;
import main.java.com.hit.dm.DataModel;
import main.java.com.hit.memory.CacheUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class CacheUnitService<T> {
    protected CacheUnit<T> cacheUnit;
    protected IDao<Long, DataModel<T>> iDao;
    protected LRUAlgoCacheImpl<Long, DataModel<T>> lruAlgoCache;
    // this members is for the statistics
    protected int totalNumberOfRequests;
    protected int totalNumberOfDM;
    protected int totalNumberSwaps;
    protected int capacity = 4;
    protected String algorithm = "LRU";

    public int getTotalNumberOfRequests() {
        return totalNumberOfRequests;
    }

    public int getTotalNumberOfDM() {
        return totalNumberOfDM;
    }

    public int getTotalNumberSwaps() {
        return totalNumberSwaps;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * constructor
     */
    public CacheUnitService() {
        lruAlgoCache = new LRUAlgoCacheImpl<Long, DataModel<T>>(capacity);
        iDao = new DaoFileImpl<T>("src/main/resources/datasourse.json");
        cacheUnit = new CacheUnit<T>(lruAlgoCache);
        totalNumberOfRequests = 0 ;
        totalNumberOfDM = 0;
        totalNumberSwaps = 0;
    }

    /**
     * @param dataModels array of data models to get from the RAM / file
     * @return array of data model with the content.
     * if the data was in the RAM: return it with content.
     * else, swap the data model from the file, if exit there, and return the content.
     */
    DataModel<T>[] get(DataModel<T>[] dataModels) {
        totalNumberOfRequests++;
        totalNumberOfDM += dataModels.length;

        Long[] idsArr = new Long[dataModels.length];
        for (int i = 0; i < dataModels.length; i++) {
            idsArr[i] = dataModels[i].getDataModelId();
        }
        DataModel<T>[] contantArr = cacheUnit.getDataModels(idsArr);
        List<DataModel<T>> elementsToReaplceArr = new ArrayList<DataModel<T>>();
        for (int i = 0; i < contantArr.length; i++) {
            if (contantArr[i] == null) {
                DataModel<T> elemToReplace = iDao.find(idsArr[i]);
                System.out.println(elemToReplace);
                contantArr[i] = elemToReplace;
                if (elemToReplace != null) {
                    elementsToReaplceArr.add(elemToReplace);
                }
            }
        }
        DataModel<T>[] temp = new DataModel[elementsToReaplceArr.size()];
        temp = elementsToReaplceArr.toArray(temp);
        DataModel<T>[] removedDataModelsArr = cacheUnit.putDataModels(temp);
        for (DataModel<T> elemToSave : removedDataModelsArr
        ) {
            if (elemToSave != null) {
                totalNumberSwaps++;
                iDao.save(elemToSave);
            }
        }
        return contantArr;
    }

    /**
     * @param dataModels array of data models to delete from the RAM and from the file.
     * @return true if succeed, false otherwise
     */
    boolean delete(DataModel<T>[] dataModels) {
        totalNumberOfRequests++;
        totalNumberOfDM += dataModels.length;
        try {
            for (DataModel<T> elemToDelete : dataModels
            ) {
                iDao.delete(elemToDelete);
            }
            Long[] idsArr = new Long[dataModels.length];
            for (int i = 0; i < dataModels.length; i++) {
                idsArr[i] = dataModels[i].getDataModelId();
            }
            cacheUnit.removeDataModels(idsArr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param dataModels array of data models to update the content by id from the RAM and from the file.
     * @return true if succeed, false otherwise
     */
    boolean update(DataModel<T>[] dataModels) {
        totalNumberOfRequests++;
        totalNumberOfDM += dataModels.length;

        try {
            DataModel<T>[] removedDM = cacheUnit.putDataModels(dataModels);
            for (DataModel<T> elem : removedDM
            ) {
                if (elem != null) {
                    totalNumberSwaps++;
                    iDao.save(elem);
                }
            }
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
     * save the data models in the RAM to the file when the server shutdown
     */
    void saveOnShutdown() {
        Map<Long, DataModel<T>> RAMContent = lruAlgoCache.getMemoryMap();
        for (Long id : RAMContent.keySet()
        ) {
            totalNumberSwaps++;
            iDao.save(RAMContent.get(id));
        }
    }
}