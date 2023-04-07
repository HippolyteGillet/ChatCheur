import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAO<T> {
    public Connection connect = ConnectionDataBaseSQL.getInstance();

    /**
     * Permet de récupérer un objet via son ID
     * @param id
     * @return
     */

    public abstract T find(int id) throws ClassNotFoundException;

    /**
     * Permet de créer une entrée dans la base de données
     * par rapport à un objet
     * @param object
     */
    public abstract T create(T object);

    /**
     * Permet de mettre à jour les données d'une entrée dans la base
     * @param object
     */
    public abstract T update(T object) throws SQLException;

    /**
     * Permet la suppression d'une entrée de la base
     * @param object
     */
    public abstract void delete(T object);
}
