package DAO;

import modele.Message;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.ResultSet;

public class MessageDao implements DAO<Message> {
    private final Connection connect = ConnectionDataBaseSQL.getInstance();

    //Méthode pour récupérer un message dans la BDD
    @Override
    public Message find(int id) {
        try (Statement statement = this.connect.createStatement()) {
            //On crée la requête SQL pour trouver le log en fonction de l'id dans la BDD
            ResultSet rs = statement.executeQuery("SELECT * FROM message WHERE id=" + id);
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
            statement.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Message create(Message message) {
        try (Statement statement = this.connect.createStatement()) {
            //On crée la requête SQL pour ajouter un message dans la BDD
            statement.executeUpdate("INSERT INTO chatcheur.message (message.USER_ID, message.TIMESTAMP, message.CONTENT) VALUES (" + message.getUser_id() +
                    ",'" + Timestamp.valueOf(message.getLocalDateTime()) + "','" + message.getContent() + "')");
            //On ferme les connections
            statement.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public Message update(Message message) {
        try (Statement statement = this.connect.createStatement()) {
            //On crée la requête SQL pour mettre à jour un modele.message dans la BDD
            statement.executeUpdate("UPDATE message SET USER_ID='" + message.getUser_id() +
                    "', TIMESTAMP='" + Timestamp.valueOf(message.getLocalDateTime()) +
                    "', CONTENT='" + message.getContent() +
                    "'WHERE ID=" + message.getId());
            //On ferme les connections
            statement.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return message;
    }

    @Override
    public void delete(int id) {
        try (Statement statement = this.connect.createStatement()) {
            statement.executeUpdate("DELETE FROM message WHERE id=" + id);
            //On ferme les connections
            statement.close();
            connect.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
