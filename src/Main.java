import DAO.*;
import modele.*;
import modele.user.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ConnectionDataBaseSQL.accessDriver();

        /*// Information d'accès à la base de données
        String url = "jdbc:mysql://localhost/chatcheur";
        String login = "root";
        String passwd = "";
        Connection cn = null;

        try {
            // Etape 1 : Chargement du driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Etape 2 : récupération de la connexion
            cn = DriverManager.getConnection(url, login, passwd);

            MessageDao messageDao = new MessageDao(cn);
            Message message = new Message(messageDao.find(1));
            System.out.println(message);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // Etape 6 : libérer ressources de la mémoire.
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }*/
        MessageDao messageDao = new MessageDao();
        //UserDAO userDAO = new UserDAO();
        //User user = new User(userDAO.newIdUser(), "userName", "password", "email", "firstName", "lastName", User.State.ONLINE, LocalDate.now());

        // Test create() OK
        //userDAO.create(user);

        // Test newIdUser() OK
        //System.out.println(userDAO.newIdUser());

        // Test find() OK
        //System.out.println(userDAO.find(1));

        // Test delete() OK
        //userDAO.delete(userDAO.find(1));

        // Test update() OK
        //user.setUserName("new_username");
        //userDAO.update(user);
        //System.out.println(user);

        // Test allClose() OK
        //userDAO.allClose();
    }
}