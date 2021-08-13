package main.java.com.hit.memory;

import com.mbj.algorithm.IAlgoCache;
import main.java.com.hit.dm.DataModel;

public class CacheUnit<T>
        extends java.lang.Object {

    private IAlgoCache<Long, DataModel<T>> algo;
    static long keyCounter = 0;

    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo) {
        this.algo = algo;
    }

    /**
     * getDataModels
     * @param ids
     * @return
     */
    public DataModel<T>[] getDataModels(Long[] ids) {
        DataModel<T>[] returnArr = new DataModel[ids.length];
        for (int i = 0; i < ids.length; i++) {
            returnArr[i] = algo.getElement(ids[i]);
        }
        return returnArr;
    }

    /**
     * putDataModels
     * @param datamodels
     * @return
     */
    public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) {
        DataModel<T>[] returnArr = new DataModel[datamodels.length];
        for (int i = 0; i < datamodels.length; i++) {
            returnArr[i] = algo.putElement(datamodels[i].getDataModelId(), datamodels[i]);
        }
        return returnArr;

    }

    /**
     * removeDataModels
     * @param ids
     */
    public void removeDataModels(Long[] ids) {
        for (long key : ids
        ) {
            algo.removeElement(key);
        }
    }

}
