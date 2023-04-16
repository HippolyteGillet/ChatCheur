import DAO.ConnectionDataBaseSQL;
import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import controller.ClientController;
import model.Log;
import model.Message;
import model.user.User;
import view.Menu;

import javax.swing.text.View;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
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
        User userModel = new User();
        List<User> usersModel = userDao.retrieveUsersFromDB();
        List<Message> messagesModel = messageDao.retrieveMessagesFromDB();
        List<Log> logsModel = logDao.retrieveLogsFromDB();
        //Create a view
        Menu view = new Menu();
        //Create the controller
        ClientController controller = new ClientController(usersModel, logsModel, messagesModel, view);

        //
        view.getButton().addActionListener(controller);
        Scanner sc = new Scanner(System.in);

        //ask for a name:
        System.out.println("Enter your Username: ");
        name = sc.nextLine();


        try (Socket socket = new Socket("localhost", 9000)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // reading from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // sending the name to the server
            String line = null;

            out.println(name + " has joined the chat");
            out.flush();
            //TODO: Avertir avec view que le client a rejoint le chat

            ThreadToDisplay threadToDisplay = new ThreadToDisplay(in);

            threadToDisplay.start();

            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();
                switch (line) {
                    case "exit":
                        threadToDisplay.interrupt();
                        System.out.println("You have left the chat");
                        out.println(name + " has left the chat :(");
                        out.flush();
                        //TODO: Avertir avec view que le client a quitté le chat

                        return;

                    default:
                        out.println(name + ": " + line);
                        out.flush();
                        break;
                }


            }

            // closing the scanner object
            sc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}