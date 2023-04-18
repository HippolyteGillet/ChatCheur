package controller;

import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import model.Log;
import model.Message;
import model.user.User;
import server.ThreadToDisplay;
import view.Home;
import view.Menu;

import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.net.*;

public class ClientController implements ActionListener {
    private final Menu view1;
    private Home view2;
    private User user;
    private List<User> users;
    private List<Log> logs;
    private List<Message> messages;
    private LogDao logDao = new LogDao();
    private MessageDao messageDao = new MessageDao();
    private UserDao userDao = new UserDao();

    public ClientController(List<User> users, List<Log> logs, List<Message> messages, Menu view) {
        this.view2 = null;
        this.user = null;
        this.users = users;
        this.logs = logs;
        this.messages = messages;
        this.view1 = view;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Log> getLogs() {
        return logs;
    }

    public void setLogs(List<Log> logs) {
        this.logs = logs;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void connection(String username, String psw) {
        boolean userFinded = false;
        //On parcourt tous les users
        for (User user : this.users) {
            //On cherche un user avec le nom et le mdp correspondent
            if (user.getUserName().equals(username) || user.getPassword().equals(psw)) {
                if (user.getUserName().equals(username) && user.getPassword().equals(psw)) {
                    userFinded = true;
                    System.out.println("User trouve : " + username);
                    //On regarde si le user est banni
                    if (user.getAccess().equals(User.Access.ACCEPTED)) {
                        System.out.println("Connexion autorisee");

                        this.user = user;
                        this.user.setState(User.State.ONLINE);
                        //Création d'un log connection
                        Log logConnection = new Log(user.getId(), Log.TypeLog.CONNECTION);
                        //On ajoute le log dans la BDD
                        logDao.create(logConnection);
                    } else {
                        System.out.println("Connexion refusee, le user est banni");
                    }
                } else if (user.getUserName().equals(username)) {
                    userFinded = true;
                    System.out.println("Mdp incorrect");
                }
            }
        }
        if (this.user == null && !userFinded) {
            System.out.println("Aucun utilisateur trouve");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Connexion":
                connection(view1.getUsername(), view1.getPassword());
                if (user != null) {
                    try {
                        view1.dispose();
                        view2 = new Home(users, logs, messages, view1.getUsername());
                    } catch (IOException | FontFormatException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
        }
    }

    //Listener pour bouton connection

}
