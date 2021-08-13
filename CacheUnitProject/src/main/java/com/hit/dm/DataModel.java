package main.java.com.hit.dm;

import java.io.Serializable;
import java.util.Objects;

public class DataModel<T>
        extends Object
        implements Serializable {
    /**
     * data model is a piece of memory,
     * it contains 2 properties:
     * id: the id of the data model
     * content: the content of the data model
     */
    private Long dataModelId;
    private T content;


    /**
     * constructor
     * @param dataModelId
     * @param content
     */
    public DataModel(Long dataModelId, T content) {
        this.dataModelId = dataModelId;
        this.content = content;
    }

    public Long getDataModelId() {
        return dataModelId;
    }

    @Override
    public String toString() {
        return "DataModelId=" + dataModelId +
                ", Content=" + content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DataModel)) return false;
        DataModel<?> dataModel = (DataModel<?>) o;
        return getDataModelId().equals(dataModel.getDataModelId()) && getContent().equals(dataModel.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDataModelId(), getContent());
    }

    public void setDataModelId(Long dataModelId) {
        this.dataModelId = dataModelId;
    }


    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public DataModel() {
    }
}
