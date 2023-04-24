package DAO;

import java.sql.Connection;

public interface DAO<T> {

    /**
     * Allows you to retrieve an object from the DB via its ID
     *
     * @param id ID is single
     * @return T
     * The function returns a created object since the database
     */
    T find(int id);

    /**
     * Allows to send object's datas in the database
     *
     * @param object Object is that we want to send in the database
     *
     */
    T create(T object);

    /**
     * Allows to update object's datas in the database
     *
     * @param obj obj is the object that we want tu update in the database
     */
    T update(T obj);

    /**
     * Allows the deletion of an object in the database
     *
     * @param id The ID is the only identification of the object
     */
    void delete(int id);
}
