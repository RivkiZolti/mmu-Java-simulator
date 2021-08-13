package main.java.com.hit.dao;

public interface IDao<ID extends java.io.Serializable, T> {
    /**
     * Deletes a given entity.
     * @param entity - given entity.
     * Throws:
     * java.lang.IllegalArgumentException - in case the given entity is null.
     */
    public void delete(T entity);

    /**
     * Retrieves an entity by its id.
     * @param id - must not be null.
     * @return the entity with the given id or null if none found
     * Throws:
     * java.lang.IllegalArgumentException - if id is null
     */
    public T find(ID id);

    /**
     * Saves a given entity.
     * @param entity - given entity
     */
    public void save(T entity);
}
