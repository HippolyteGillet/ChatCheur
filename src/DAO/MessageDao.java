package DAO;

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

public class MessageDao implements DAO<Message> {
    private final Connection connect = ConnectionDataBaseSQL.getInstance();

    //Méthode pour récupérer un message dans la BDD
    @Override
    public Message find(int id) {
        try (Statement statement = this.connect.createStatement()) {
            //On crée la requête SQL pour trouver le log en fonction de l'id dans la BDD
            ResultSet rs = statement.executeQuery("SELECT * FROM message WHERE ID =" + id);
            if (rs.next()) {
                //On récupère les informations nécessaires
                int user_id = rs.getInt("USER_ID");
                String content = rs.getString("CONTENT");
                Timestamp timeStamp = Timestamp.valueOf(rs.getString("TIMESTAMP"));
                //On retourne l'objet
                return new Message(id, user_id, timeStamp.toLocalDateTime(), content);
            }
            //On ferme les connections
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message create(Message message) {
        try (Statement statement = this.connect.createStatement()) {
            statement.executeUpdate("ALTER TABLE message AUTO_INCREMENT = 1;");
            //On crée la requête SQL pour ajouter un message dans la BDD
            statement.executeUpdate("INSERT INTO chatcheur.message (message.USER_ID, message.TIMESTAMP, message.CONTENT) VALUES (" + message.getUser_id() +
                    ",'" + Timestamp.valueOf(message.getLocalDateTime()) + "','" + message.getContent() + "')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public Message update(Message message) {
        try (Statement statement = this.connect.createStatement()) {
            statement.executeUpdate("ALTER TABLE message AUTO_INCREMENT = 1;");

            //On crée la requête SQL pour mettre à jour un modele.message dans la BDD
            statement.executeUpdate("UPDATE message SET USER_ID='" + message.getUser_id() +
                    "', TIMESTAMP='" + Timestamp.valueOf(message.getLocalDateTime()) +
                    "', CONTENT='" + message.getContent() +
                    "'WHERE ID=" + message.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void delete(int id) {
        try (Statement statement = this.connect.createStatement()) {
            statement.executeUpdate("ALTER TABLE message AUTO_INCREMENT = 1;");
            statement.executeUpdate("DELETE FROM message WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Message> retrieveMessagesFromDB() {
        List<Message> messages = new ArrayList<>();
        int id = 0;
        try {
            ResultSet rs = this.connect.createStatement().executeQuery("SELECT * FROM message");
            do {
                id++;
                if (find(id) != null) {
                    messages.add(find(id));
                }
            } while (rs.next());
            //On ferme les connections
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public Integer retrieveMessagesEachHour(LocalDateTime beginHour, LocalDateTime endHour) {
        int i = 0;
        try {
            ResultSet rs = this.connect.createStatement().executeQuery("SELECT * FROM message WHERE message.TIMESTAMP BETWEEN '" + beginHour + "' AND '" + endHour + "'");
            while (rs.next()) {
                i++;
            }
            //On ferme les connections
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public ArrayList<Integer> findTopUsers() {
        ArrayList<Integer> idTopUsers = new ArrayList<>();
        int id = 0;
        try {
            ResultSet rs = this.connect.createStatement().executeQuery("SELECT USER_ID, COUNT(*) AS count FROM message GROUP BY USER_ID ORDER BY count DESC LIMIT 3");
            while (rs.next()) {
                id = rs.getInt("USER_ID");
                idTopUsers.add(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idTopUsers;
    }

    //Méthode pour compter le nombre de messages dans la BDD
    public int getLastID() {
        int count = 0;
        try {
            ResultSet rs = this.connect.createStatement().executeQuery("SELECT COUNT(*) AS count FROM message");
            if (rs.next()) {
                count = rs.getInt("count");
            }
            //On ferme les connections
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
