package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDataBaseSQL {
    public static void main(String[] arg) {
        Connection connect = ConnectionDataBaseSQL.getInstance();
        try (Statement statement = connect.createStatement()) {
            //----------------------SCHEMA-MYSQL----------------------------------------
            //On supprime la BDD si elle existe
            statement.executeUpdate("DROP DATABASE IF EXISTS chatcheur");
            //On crée la BDD
            statement.executeUpdate("CREATE DATABASE chatcheur DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;");
            //On crée la table user
            statement.executeUpdate("CREATE TABLE chatcheur.user (" +
                    "    ID                int NOT NULL AUTO_INCREMENT," +
                    "    USER_NAME         varchar(255) DEFAULT NULL," +
                    "    FIRST_NAME        varchar(255) DEFAULT NULL," +
                    "    LAST_NAME         varchar(255) DEFAULT NULL," +
                    "    EMAIL             varchar(255) DEFAULT NULL," +
                    "    PASSWORD          varchar(255) DEFAULT NULL," +
                    "    PERMISSION        enum ('ADMINISTRATOR','MODERATOR','USER')," +
                    "    LAST_CONNECTION_T timestamp    DEFAULT CURRENT_TIMESTAMP," +
                    "    ACCESS            enum ('ACCEPTED','BANNED')," +
                    "    STATE             enum ('ONLINE','OFFLINE','AWAY')," +
                    "    PRIMARY KEY (ID))");
            //On crée la table log
            statement.executeUpdate("CREATE TABLE chatcheur.log (" +
                    "   ID        int NOT NULL AUTO_INCREMENT," +
                    "   USER_ID   varchar(255) DEFAULT NULL," +
                    "   TIMESTAMP timestamp    DEFAULT CURRENT_TIMESTAMP," +
                    "   TYPELOG   enum ('CONNECTION', 'DISCONNECTION', 'MESSAGE', 'BAN', 'UNBAN', 'SET_ROLE', 'UNKNOWN')," +
                    "   PRIMARY KEY (ID));");
            //On crée la table message
            statement.executeUpdate("CREATE TABLE chatcheur.message (" +
                    "   ID        int NOT NULL AUTO_INCREMENT," +
                    "   USER_ID   varchar(255) DEFAULT NULL," +
                    "   TIMESTAMP timestamp    DEFAULT CURRENT_TIMESTAMP," +
                    "   CONTENT   varchar(255) DEFAULT NULL," +
                    "   PRIMARY KEY (ID))");
            statement.executeUpdate("ALTER TABLE chatcheur.message AUTO_INCREMENT = 1;");

            //-----------------------DATA-MYSQL--------------------------------------------
            //On insert data dans user
            statement.executeUpdate("INSERT INTO chatcheur.user (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, " +
                    "PERMISSION, LAST_CONNECTION_T, ACCESS, STATE) VALUES ('Gaby', 'gabriel', 'henaux', 'gaby.henaux@gmail.com', 'e4feeef0e2f7ae25ab42a8ab7e3dfd1c6e3181b5321580b9d351952358dd1eb0', 'ADMINISTRATOR', CURRENT_TIMESTAMP, 'banned', 'OFFLINE')");
            statement.executeUpdate("INSERT INTO chatcheur.user (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, " +
                    "PERMISSION, LAST_CONNECTION_T, ACCESS, STATE) VALUES ('Stan', 'hippolyte', 'gillet', 'hippo.gillet@gmail.com', '0600a614e3541ebfbbdee287d5269a7615a3dd1cf17887bad36b4e6223fd1fa0', 'ADMINISTRATOR', CURRENT_TIMESTAMP," +
                    "   'ACCEPTED', 'OFFLINE')");
            statement.executeUpdate("INSERT INTO chatcheur.user (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, " +
                    "PERMISSION, LAST_CONNECTION_T, ACCESS, STATE) VALUES ('Chofo', 'nathan', 'outrey', 'nathoudu66@gmail.com', '0055e0a0380c5ea7a739d505decc453131c5b308f2a1936110b40a6cd828eb5b', 'ADMINISTRATOR', CURRENT_TIMESTAMP, 'ACCEPTED'," +
                    "   'OFFLINE')");
            statement.executeUpdate("INSERT INTO chatcheur.user (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, " +
                    "PERMISSION, LAST_CONNECTION_T, ACCESS, STATE) VALUES ('jujulafondue', 'juliette', 'lafond', 'juliettelafond@gmail.com', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 'USER', CURRENT_TIMESTAMP," +
                    "   'ACCEPTED', 'OFFLINE')");
            statement.executeUpdate("INSERT INTO chatcheur.user (USER_NAME, FIRST_NAME, LAST_NAME, EMAIL, PASSWORD, " +
                    "PERMISSION, LAST_CONNECTION_T, ACCESS, STATE) VALUES ('nul', 'nul', 'nul', 'nul@gmail.com', '15e2b0d3c33891ebb0f1ef609ec419420c20e320ce94c65fbc8c3312448eb225', 'MODERATOR', CURRENT_TIMESTAMP," +
                    "   'ACCEPTED', 'OFFLINE')");
            //On insert data dans message
            statement.executeUpdate("INSERT INTO chatcheur.message (USER_ID, TIMESTAMP, CONTENT) VALUES (2, CURRENT_TIMESTAMP, 'Coucou comment vas-tu ?')");
            statement.executeUpdate("INSERT INTO chatcheur.message (USER_ID, TIMESTAMP, CONTENT) VALUES (3, CURRENT_TIMESTAMP, 'Au top :)')");
            //On insert data dans log
            statement.executeUpdate("INSERT INTO chatcheur.log (USER_ID, TIMESTAMP, TYPELOG)" +
                    "VALUES (5, CURRENT_TIMESTAMP, 'CONNECTION')");
            statement.executeUpdate("INSERT INTO chatcheur.log (USER_ID, TIMESTAMP, TYPELOG)\n" +
                    "VALUES (1, CURRENT_TIMESTAMP,'DISCONNECTION')");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
