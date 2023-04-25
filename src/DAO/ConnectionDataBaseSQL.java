package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDataBaseSQL{

    /**
     * Connection URL
     */
    private static String url = "jdbc:mysql://localhost:8889/chatcheur";
    /**
     * Name's user
     */
    private static String user = "root";
    /**
     * Password's user
     */
    private static String passwd = "root";
    /**
     * Object Connexion
     */
    private static Connection connect;

    /**
     * Function who returns our instance or
     * create it if it doesn't exist
     * @return connect
     */
    public static Connection getInstance(){
        if(connect == null){
            try {
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }

    /**
     * Access to Driver
     */
    public static void accessDriver(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}