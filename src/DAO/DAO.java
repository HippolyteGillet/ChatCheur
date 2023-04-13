package DAO;

import java.sql.Connection;
import java.sql.SQLException;

public interface DAO<T> {
    Connection connect = ConnectionDataBaseSQL.getInstance();

    /**
     * Permet de récupérer un objet via son ID
     * @param id
     * @return
     */

     T find(int id);

    /**
     * Permet de créer une entrée dans la base de données
     * par rapport à un objet
     * @param object
     */
    T create(T object);

    /**
     * Permet de mettre à jour les données d'une entrée dans la base
     * @param object
     */
    T update(T object);

    /**
     * Permet la suppression d'une entrée de la base
     * @param object
     */
    void delete(T object);
}
