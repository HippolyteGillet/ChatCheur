import DAO.ConnectionDataBaseSQL;
import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import server.*;
import controller.ClientController;
import model.Log;
import model.Message;
import model.user.User;
import view.Menu;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.List;

// Client class
class Client {

    static String name;

    Client(String name) throws IOException, FontFormatException {
        this.name = name;
    }

    public static void main(String[] args) throws IOException, FontFormatException {
        //---------------------------------------INITIALISATION------------------------------------
        //Create the connection to the DB
        ConnectionDataBaseSQL.accessDriver();

        //Create all dao class to retrieve data from the database
        UserDao userDao = new UserDao();
        LogDao logDao = new LogDao();
        MessageDao messageDao = new MessageDao();
        //Create all the model and retrieve the data stored in the database
        List<User> usersModel = userDao.retrieveUsersFromDB();
        List<Message> messagesModel = messageDao.retrieveMessagesFromDB();
        List<Log> logsModel = logDao.retrieveLogsFromDB();

        // Create initial colors
        Color C1 = new Color(246, 245, 254);
        Color C2 = new Color(251, 108, 122);
        Color C3 = new Color( 21,21,21);
        Color C4 = new Color(111,35,255);
        Color C5 = new Color(128,101,254);
        Color C6 = Color.BLACK;

        //Create a view
        Menu view = new Menu(usersModel, logsModel, messagesModel, C1, C2, C3, C4, C5, C6);

        //---------------------------------------SERVER PART----------------------------------------------
        Socket socket = new Socket("localhost", 8999);
        ClientHandler threadToDisplay = new ClientHandler(new BufferedReader(new InputStreamReader(socket.getInputStream())),
                new ClientController(usersModel, logsModel, messagesModel, view, socket));
        threadToDisplay.start();
    }
}
