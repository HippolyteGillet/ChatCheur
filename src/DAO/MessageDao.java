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

    /**
     * Function allows us to find an element in the Messages table with an ID
     * @param id ID is single
     * @return a Message with the ID
     */
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

    /**
     * Create a new log in the database
     * @param message Object is that we want to send in the database
     * @return message
     */
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

    /**
     * Update the database
     * @param message obj is the object that we want tu update in the database
     * @return message
     */
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

    /**
     * Delete a Message with an ID
     * @param id The ID is the only identification of the object
     */
    @Override
    public void delete(int id) {
        try (Statement statement = this.connect.createStatement()) {
            statement.executeUpdate("ALTER TABLE message AUTO_INCREMENT = 1;");
            statement.executeUpdate("DELETE FROM message WHERE id=" + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves all the messages from database
     * @return all messages
     */
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

    /**
     * We retrieve the number of messages sent during an hour
     * @param beginHour or first hour
     * @param endHour or final hour
     * @return messages - number of messages between two hours
     */
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

    /**
     * find the 3 top users who sent more messages
     * @return a ArrayList with the 3 id of the top users
     */
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

    /**
     * Retrieve the last ID in the table
     * @return last ID
     */
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
