package DAO;

import model.Log;
import model.Message;
import model.user.User;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogDao implements DAO<Log> {

    /**
     * We create a final variable connect to get have access to the database
     * with getInstance()
     */
    private final Connection connect = ConnectionDataBaseSQL.getInstance();

    /**
     * Function allows us to find an element in the Log table with an ID
     * @param id ID is single
     * @return a Log with this id
     */
    @Override
    public Log find(int id) {
        try (Statement statement = this.connect.createStatement()) {
            //On crée la requête SQL pour trouver le log en fonction de l'id dans la BDD
            ResultSet rs = statement.executeQuery("SELECT * FROM log WHERE ID=" + id);
            if (rs.next()) {
                //On récupère les informations nécessaires
                int user_id = rs.getInt("USER_ID");
                Log.TypeLog type = Log.TypeLog.valueOf(rs.getString("TYPELOG").toUpperCase());
                Timestamp timeStamp = Timestamp.valueOf(rs.getString("TIMESTAMP"));
                //On retourne l'objet
                return new Log(id, user_id, timeStamp.toLocalDateTime(), type);
            }
            //On ferme les connections
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a new log in the database
     * @param log Object is that we want to send in the database
     * @return log
     */
    @Override
    public Log create(Log log) {
        try (Statement statement = this.connect.createStatement()) {
            //On crée la requête SQL pour ajouter un modele.log dans la BDD
            statement.executeUpdate("INSERT INTO chatcheur.log (log.USER_ID, log.TIMESTAMP, log.TYPELOG) VALUES (" + log.getUser_id() +
                    ",'" + Timestamp.valueOf(log.getLocalDateTime()) + "','" + log.getType() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return log;
    }

    //Méthode pour modifier les valeurs d'un log dans la BDD

    /**
     * Update the database
     * @param log obj is the object that we want tu update in the database
     * @return log
     */
    @Override
    public Log update(Log log) {
        try (Statement statement = this.connect.createStatement()) {
            //On crée la requête SQL pour mettre à jour un modele.log dans la BDD
            statement.executeUpdate("UPDATE log SET user_id='" + log.getUser_id() +
                    "', timeStamp='" + Timestamp.valueOf(log.getLocalDateTime()) +
                    "', typelog='" + log.getType() +
                    "'WHERE id=" + log.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return log;
    }

    /**
     * Delete a Log with an ID
     * @param id The ID is the only identification of the object
     */
    @Override
    public void delete(int id) {
        try (Statement statement = this.connect.createStatement()) {
            statement.executeUpdate("DELETE FROM log WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all the logs from database
     * @return all logs
     */
    public List<Log> retrieveLogsFromDB() {
        List<Log> logs = new ArrayList<>();
        int id = 0;
        try {
            ResultSet rs = this.connect.createStatement().executeQuery("SELECT * FROM log");
            do {
                id++;
                if (find(id) != null) {
                    logs.add(find(id));
                }
            } while (rs.next());
            //On ferme les connections
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    /**
     * We retrieve the number of connections during an hour
     * @param beginHour or first hour
     * @param endHour or final hour
     * @return logs - number of connections between two hours
     */
    public Integer findConnectionsPerHour(LocalDateTime beginHour, LocalDateTime endHour) {
        int logs = 0;
        try (Statement statement = this.connect.createStatement()) {

            //On crée la requête SQL pour trouver le log en fonction de l'id dans la BDD
            ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS nombreLog FROM log WHERE log.TIMESTAMP BETWEEN '" + beginHour+ "' AND '" + endHour + "'");
            while (rs.next()) {
                logs = rs.getInt("nombreLog");
            }
            //On ferme les connections
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }
}
