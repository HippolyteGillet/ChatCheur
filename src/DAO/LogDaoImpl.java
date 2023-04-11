package DAO;

import modele.Log;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;

public class LogDaoImpl implements LogDao {
    private final Connection connection;

    //On construit notre objet avec en paramètre l'adresse de notre BDD
    public LogDaoImpl(Connection connection) {
        this.connection = connection;
    }

    //Méthode pour récupérer un log dans la BDD
    @Override
    public Log find(int id) {
        try (Statement statement = connection.createStatement()) {
            //On crée la requête SQL pour trouver le log en fonction de l'id dans la BDD
            ResultSet rs = statement.executeQuery("SELECT * FROM log WHERE ID=" + id);
            if (rs.next()) {
                //On récupère les informations nécessaires
                int user_id = rs.getInt("USER_ID");
                Log.TypeLog type = Log.TypeLog.valueOf(rs.getString("TYPELOG"));
                Timestamp timeStamp = Timestamp.valueOf(rs.getString("TIMESTAMP"));
                //On retourne l'objet
                return new Log(id, user_id, timeStamp.toLocalDateTime(), type);
            }
            //On ferme les connections
            rs.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Méthode pour ajouter un log dans la BDD
    @Override
    public void create(Log log) {
        try (Statement statement = connection.createStatement()) {
            //On crée la requête SQL pour ajouter un modele.log dans la BDD
            statement.executeUpdate("INSERT INTO chatcheur.log (log.USER_ID, log.TIMESTAMP, log.TYPELOG) VALUES (" + log.getUser_id() +
                    ",'" + Timestamp.valueOf(log.getLocalDateTime()) + "','" + log.getType() + "')");
            //On ferme les connections

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Méthode pour modifier les valeurs d'un log dans la BDD
    @Override
    public void update(Log log) {
        try (Statement statement = connection.createStatement()) {
            //On crée la requête SQL pour mettre à jour un modele.log dans la BDD
            statement.executeUpdate("UPDATE log SET user_id='" + log.getUser_id() +
                    "', timeStamp='" + Timestamp.valueOf(log.getLocalDateTime()) +
                    "', typelog='" + log.getType() +
                    "'WHERE id=" + log.getId());
            //On ferme les connections
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Méthode pour supprimer un modele.log dans la BDD
    @Override
    public void delete(int id) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM log WHERE id=" + id);
            //On ferme les connections
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
