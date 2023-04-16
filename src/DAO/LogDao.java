package DAO;

import model.Log;
import model.user.User;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LogDao implements DAO<Log> {
    private final Connection connect = ConnectionDataBaseSQL.getInstance();

    //Méthode pour récupérer un log dans la BDD
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

    //Méthode pour ajouter un log dans la BDD
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

    //Méthode pour supprimer un modele.log dans la BDD
    @Override
    public void delete(int id) {
        try (Statement statement = this.connect.createStatement()) {
            statement.executeUpdate("DELETE FROM log WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
}
