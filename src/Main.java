import DAO.*;
import model.*;
import view.Stats;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
        UserDao userDao = new UserDao();
        LogDao logDao = new LogDao();

    }
}