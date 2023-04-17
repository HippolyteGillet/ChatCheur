package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDataBaseSQL {
    public static void main(String[] arg) {
        Connection connect = ConnectionDataBaseSQL.getInstance();
        try (Statement statement = connect.createStatement()) {
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
                            "    PERMISSION        enum ('ADMIN','MODERATOR','NORMAL')," +
                            "    LAST_CONNECTION_T timestamp    DEFAULT CURRENT_TIMESTAMP," +
                            "    ACCESS            enum ('ACCEPTED','BANNED')," +
                            "    STATE             enum ('ONLINE','OFFLINE','AWAY')," +
                            "    PRIMARY KEY (ID));");
            //On crée la table log
            statement.executeUpdate("CREATE TABLE chatcheur.log (" +
                    "   ID        int NOT NULL AUTO_INCREMENT," +
                    "   USER_ID   varchar(255) DEFAULT NULL," +
                    "   TIMESTAMP timestamp    DEFAULT CURRENT_TIMESTAMP," +
                    "   TYPELOG   enum ('CONNECTION','DISCONNECTION','SENT','BAN','STATEUPDATE')," +
                    "   PRIMARY KEY (ID),\n" +
                    "   FOREIGN KEY (USER_ID) REFERENCES chatcheur.user (ID));");
            //On crée la table message
            statement.executeUpdate("CREATE TABLE chatcheur.message (" +
                    "   ID        int NOT NULL AUTO_INCREMENT," +
                    "   USER_ID   varchar(255) DEFAULT NULL," +
                    "   TIMESTAMP timestamp    DEFAULT CURRENT_TIMESTAMP," +
                    "   CONTENT   varchar(255) DEFAULT NULL," +
                    "   PRIMARY KEY (ID)," +
                    "   FOREIGN KEY (USER_ID) REFERENCES chatcheur.user (ID));");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
