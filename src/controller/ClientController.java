package controller;

import model.Log;
import model.Message;
import model.user.User;
import view.Menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientController implements ActionListener {
    private final Menu view;
    private User user;
    private List<User> users;
    private List<Log> logs;
    private List<Message> messages;

    public ClientController(List<User> users, List<Log> logs, List<Message> messages, Menu view) {
        this.user = null;
        this.users = users;
        this.logs = logs;
        this.messages = messages;
        this.view = view;
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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Connexion":
                connection(view.getUsername(), view.getPassword());
        }
    }

    //Listener pour bouton connection

}
