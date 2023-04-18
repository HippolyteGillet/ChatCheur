package controller;

import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import model.Log;
import model.Message;
import model.user.User;
import server.ThreadToDisplay;
import view.Home;
import view.LogOut;
import view.Menu;
import view.NewPassword;

import javax.swing.*;
import javax.swing.text.Style;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.net.*;

public class ClientController implements ActionListener {
    private Menu view1;
    private Home view2;
    private LogOut view3;
    private User user;
    private List<User> users;
    private List<Log> logs;
    private List<Message> messages;
    private LogDao logDao = new LogDao();
    private MessageDao messageDao = new MessageDao();
    private UserDao userDao = new UserDao();

    private NewPassword newPassword;

    public ClientController(List<User> users, List<Log> logs, List<Message> messages, Menu view) {
        this.view2 = null;
        this.user = null;
        this.users = users;
        this.logs = logs;
        this.messages = messages;
        this.view1 = view;
        view1.addAllListener(this);
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
            //chiffrer le mdp en SHA-256

            if (user.getUserName().equals(username)) {
                if (user.getUserName().equals(username) && user.getPassword().equals(sha256(psw))) {
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

                        gererFenetresConnection();
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

    public void gererFenetresConnection() {
        //On crée fenetre
        try {
            //On supprime menu
            this.view1.dispose();
            this.view2 = new Home(users, logs, messages, view1.getUsername());
            //On met la 1ere fenetre a null
            this.view1 = null;
            this.view2.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void disconnection() {
        Log logDeconnection = new Log(user.getId(), Log.TypeLog.DISCONNECTION);
        logDao.create(logDeconnection);
        System.out.println("Utilisateur deconnecte : " + user.getUserName());
        this.user = null;
        gererFenetresDisconnection();
    }

    public void gererFenetresDisconnection() {
        //On ferme les autres fenetres
        view3.dispose();
        view3 = null;
        view2.dispose();
        view2 = null;
        //On crée la fenetre de base
        try {
            view1 = new Menu(users, logs, messages);
            view1.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void gererFenetresLogOut() {
        view3 = new LogOut(view2);
        view3.setVisible(true);
        view3.addAllListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Connexion" -> {
                connection(view1.getUsername(), view1.getPassword());

                if (user != null) {
                    try {
                        view1.dispose();
                        view2 = new Home(users, logs, messages, view1.getUsername());
                    } catch (IOException | FontFormatException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            case "Ok !" -> {
                user = userDao.findUserName(newPassword.getUserName());
                user.setPassword(newPassword.getPsswrd());
                userDao.update(user);
            }

                
            case "logOut" -> {
                gererFenetresLogOut();
            }

            case "Disconnection" -> {
                disconnection();
            }
            case "Send" -> {
                send(view2.getTextField1().getText());
            }

        }
    }

    public static String sha256(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    //Listener pour bouton connection

}
