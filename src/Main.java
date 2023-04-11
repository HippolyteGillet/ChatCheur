import DAO.*;
import modele.*;

import java.sql.*;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Information d'accès à la base de données
        String url = "jdbc:mysql://localhost/chatcheur";
        String login = "root";
        String passwd = "";
        Connection cn = null;

        try {
            // Etape 1 : Chargement du driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Etape 2 : récupération de la connexion
            cn = DriverManager.getConnection(url, login, passwd);

            LogDao logDao = new LogDaoImpl(cn);
            logDao.delete(1);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            try {
                // Etape 6 : libérer ressources de la mémoire.
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}