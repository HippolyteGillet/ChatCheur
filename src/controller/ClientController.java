package controller;

import DAO.LogDao;
import DAO.MessageDao;
import model.Log;
import model.Message;
import model.user.User;
import view.Home;
import view.Menu;

import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ClientController implements ActionListener {
    //TODO: add an attribute to have all the DAO
    private final Menu view1;
    private Home view2;
    private User user;
    private List<User> users;
    private List<Log> logs;
    private List<Message> messages;

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
        //On parcourt tous les users
        for (User user : this.users) {
            //On cherche un user avec le nom et le mdp correspondent
            if (user.getUserName().equals(username) && user.getPassword().equals(psw)) {
                System.out.println("User trouve : " + username);
                //On regarde si le user est banni
                if (user.getAccess().equals(User.Access.ACCEPTED)) {
                    System.out.println("Connexion autorisee");

                    this.user = user;
                } else {
                    System.out.println("Connexion refusee, le user est banni");
                }
            }
        }
        if (user == null) {
            System.out.println("Aucun utilisateur trouve");
        }
    }

    public void send(String message) {

        if (message != null && !message.isEmpty() && user != null) {
            Message messagToSend = new Message(user.getId(), message);
            Log logToSend = new Log(user.getId(), Log.TypeLog.MESSAGE);
            //JAVA Part:
            messages.add(messagToSend);
            logs.add(logToSend);
            //SQL Part:
            try {
                //////////////!!!!!!!!!!!!!!!!!!!A FAIRE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                //TODO: appeller les gets de MessageDao et LogDao pour ajouter le message et le log dans la BDD
                MessageDao messageDao = new MessageDao();
                LogDao logDao = new LogDao();
                messageDao.create(messagToSend);
                logDao.create(logToSend);
            } catch (Exception e) {
            e.printStackTrace();
            }
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
                        view2 = new Home(users, logs, messages);
                    } catch (IOException | FontFormatException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            case "Send":
                //TODO: Creeer un button et l'activer dans Home.java
                send(view2.getTextField1().getText());
                break;
        }
    }

}
