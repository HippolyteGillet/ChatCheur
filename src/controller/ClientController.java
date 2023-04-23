package controller;

import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import model.Log;
import model.Message;
import model.user.User;
import view.*;
import view.Menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;

public class ClientController implements ActionListener {
    //DAO
    private final LogDao logDao = new LogDao();
    private final MessageDao messageDao = new MessageDao();
    private final UserDao userDao = new UserDao();
    //VIEW
    private Menu menuView;
    private Home homeView;
    private LogOut logOutView;
    private NewPassword newPasswordView;
    private Stats statsView;
    private Settings settingsView;
    private InfoUser infoUserView;
    //MODELE
    private User currentUser;
    private List<User> users;
    private List<Log> logs;
    private List<Message> messages;
    //SERVER
    private PrintWriter out;

    public ClientController(List<User> users, List<Log> logs, List<Message> messages, Menu view, Socket socket) {
        this.currentUser = null;
        this.users = users;
        this.logs = logs;
        this.messages = messages;
        this.menuView = view;
        this.homeView = null;
        this.logOutView = null;
        this.newPasswordView = null;
        menuView.addAllListener(this);
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //--------------------------CHIFFREMENT MDP-------------------------------------
    public static String sha256(String input) {
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

    public List<Message> getMessages() {
        return messages;
    }

    //--------------------------CONNECTION-------------------------------------
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

                        gererFenetresConnection();

                        this.currentUser = user;
                        this.currentUser.setState(User.State.ONLINE);
                        this.currentUser.setLastConnectionTime(LocalDateTime.now());

                        sendToServerConnection();
                        connectionToDB(this.currentUser);

                    } else {
                        System.out.println("Connexion refusee, le user est banni");
                        menuView.afficherBannissement();
                    }
                } else if (user.getUserName().equals(username)) {
                    userFinded = true;
                    System.out.println("Mdp incorrect");
                    menuView.afficherMdpIncorrect();
                }
            }
        }
        if (this.currentUser == null && !userFinded) {
            System.out.println("Aucun utilisateur trouve");
            menuView.afficherUserUknown();

        }
    }

    public void connectionToDB(User user) {
        //On met a jour BDD
        this.userDao.update(user);
        //Création d'un log connection
        Log logConnection = new Log(user.getId(), Log.TypeLog.CONNECTION);
        //On ajoute le log dans la BDD
        logDao.create(logConnection);
    }

    public void gererFenetresConnection() {
        //On crée fenetre
        try {
            //On supprime menu
            this.menuView.dispose();
            this.homeView = new Home(users, logs, messages, menuView.getUsername());
            //On met la 1ere fenetre a null
            this.menuView = null;
            this.homeView.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    //-----------------------------MESSAGE---------------------------------

    public void send(String message) {
        if (!message.equals("Saisir du texte") && !message.isEmpty() && currentUser != null) {
            Message messagToSend = new Message(currentUser.getId(), message, messageDao.getLastID() + 1);
            Log logToSend = new Log(currentUser.getId(), Log.TypeLog.MESSAGE);
            //On met a jour la vue
            homeView.setInputReceived(true);
            int y = homeView.calculY(messages);
            homeView.getScrollPane().getVerticalScrollBar().setValue(homeView.getScrollPane().getVerticalScrollBar().getMaximum());
            homeView.getconversationPanelContent().setPreferredSize(new Dimension(950, y + 60));
            homeView.getScrollPane().getViewport().setViewPosition(new Point(0, y));
            homeView.setY(y);
            homeView.getTextField1().setText(null);
            homeView.repaint();

            sendToServerMessage(messagToSend);

            try {
                messageDao.create(messagToSend);
                logDao.create(logToSend);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    //-------------------------DISCONNECTION--------------------------------------
    public void disconnection() {
        this.currentUser.setState(User.State.OFFLINE);
        sendToServerDisconnection();
        Log logDeconnection = new Log(currentUser.getId(), Log.TypeLog.DISCONNECTION);
        //On met a jour BDD
        logDao.create(logDeconnection);
        this.userDao.update(currentUser);
        this.currentUser = null;
        gererFenetresDisconnection();
    }

    public void gererFenetresDisconnection() {
        //On ferme les autres fenetres
        logOutView.dispose();
        logOutView = null;
        homeView.dispose();
        homeView = null;
        //On crée la fenetre de base
        try {
            menuView = new Menu(users, logs, messages);
            menuView.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void gererFenetresLogOut() {
        logOutView = new LogOut(homeView);
        logOutView.setVisible(true);
        logOutView.addAllListener(this);
    }

    //----------------------------------BANNISSEMENT-----------------------------------------

    public void bannissement(int i) {
        //On cree nouvelle list sans le current user
        List<User> nonCurrentUsers = new ArrayList<>();
        User userToChange = null;
        int positionIcon = 0;
        for (User user : this.users) {
            if (user.getId() != (this.currentUser.getId())) {
                nonCurrentUsers.add(user);
                if (user.getId() == i) {
                    userToChange = user;
                }
            }
        }
        if (userToChange.getId() < currentUser.getId()) {
            positionIcon = userToChange.getId() - 1;
        } else {
            positionIcon = userToChange.getId() - 2;
        }

        //Si l'utilisateur est banni, on le débanni, sinon on le banni
        if (userToChange.getAccess().equals(User.Access.BANNED)) {
            int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir débannir cet utilisateur ?", "Confirmer le débannissement", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                homeView.setIconBan(positionIcon);
                userToChange.setAccess(User.Access.ACCEPTED);
                //On met à jour la BDD
                userDao.update(userToChange);
                Log logBan = new Log(userToChange.getId(), Log.TypeLog.UNBAN);
                logDao.create(logBan);
                sendToServerBannissement(userToChange);
            }
        } else {
            int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir bannir cet utilisateur ?", "Confirmer le bannissement", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                homeView.setIconUnban(positionIcon);
                userToChange.setAccess(User.Access.ACCEPTED);
                //On met à jour la BDD
                userDao.update(userToChange);
                Log logBan = new Log(userToChange.getId(), Log.TypeLog.BAN);
                logDao.create(logBan);
                sendToServerBannissement(userToChange);
            }
        }
        homeView.repaint();
    }

    //-----------------------------------ENVOIE SERVEUR-----------------------------------------
    public void sendToServerConnection() {
        this.out.println("Connection: " + this.currentUser.getUserName() + " connected to server " + this.currentUser);
    }

    public void sendToServerMessage(Message message) {
        try {
            this.out.println("Message de " + this.currentUser.getUserName() + " : " + message);
        } catch (Exception e) {
            System.out.println("ERROR, Exception occured on sending");
        }
    }

    public void sendToServerDisconnection() {
        this.out.println("Disconnection: " + currentUser.getUserName() + " disconnected from server " + this.currentUser);
    }

    public void sendToServerNewState() {
        this.out.println("State: " + this.currentUser.getUserName() + " changed his state " + this.currentUser);
    }

    public void sendToServerBannissement(User user) {
        this.out.println("Kick: " + user.getUserName() + " kicked from server " + user);
    }

    public void sendToServerPermission(User user) {
        this.out.println("Permission: " + user.getUserName() + " new server permission " + user);
    }

    //-------------------------------------PASSWORD-------------------------------------------
    public void mdpOublie() {
        try {
            newPasswordView = new NewPassword();
            newPasswordView.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        newPasswordView.setVisible(true);
    }

    public void newMdp() {
        currentUser = userDao.findUserName(newPasswordView.getTextFieldUserName());
        currentUser.setPassword(newPasswordView.getTextFieldNewPassword());
        userDao.update(currentUser);
        currentUser = null;
        newPasswordView.dispose();
        newPasswordView = null;
    }

    //-----------------------------------STATS------------------------------------------------
    public void pageStats() {
        try {
            statsView = new Stats(getTypeUser(), getTypeModerator(), getTypeAdministrator(),
                    getUsersOnline(), getUsersAway(), getUsersOffline(),
                    getNumberBanned(), getNumberMessagesPerHour(), getNumberConnectionsPerHour(), getTopUsers());
            //view5.addAllListener(this);

        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        statsView.setVisible(true);

    }

    public ArrayList<User> getUsersOnline() {
        return userDao.findNumberUsersOnline();
    }

    public ArrayList<User> getUsersAway() {
        return userDao.findNumberUsersAway();
    }

    public ArrayList<User> getUsersOffline() {
        return userDao.findNumberUsersOffline();
    }

    public ArrayList<User> getTypeUser() {
        return userDao.findNumberUser();
    }

    public ArrayList<User> getTypeModerator() {
        return userDao.findNumberModerator();
    }

    public ArrayList<User> getTypeAdministrator() {
        return userDao.findNumberAdministrator();
    }

    public ArrayList<User> getNumberBanned() {
        return userDao.findNumberBanned();
    }

    public ArrayList<Integer> getNumberMessagesPerHour() {

        ArrayList<Integer> finalList = new ArrayList<>();
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime firstHour = LocalDateTime.of(timeNow.getYear(), timeNow.getMonth(), timeNow.getDayOfMonth() - 2, 0, 0);
        LocalDateTime secondHour = firstHour.plusHours(1);

        for (int i = 0; i < 24; i++) {
            finalList.add(messageDao.retrieveMessagesEachHour(firstHour, secondHour));
            firstHour = firstHour.plusHours(1);
            secondHour = secondHour.plusHours(1);
        }
        return finalList;
    }

    public ArrayList<Integer> getNumberConnectionsPerHour() {
        ArrayList<Integer> finalList = new ArrayList<>();
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime firstHour = LocalDateTime.of(timeNow.getYear(), timeNow.getMonth(), timeNow.getDayOfMonth(), 0, 0);
        LocalDateTime secondHour = firstHour.plusHours(1);

        for (int i = 0; i < 24; i++) {
            finalList.add(logDao.findConnectionsPerHour(firstHour, secondHour));
            firstHour = firstHour.plusHours(1);
            secondHour = secondHour.plusHours(1);
        }
        return finalList;

    }

    public ArrayList<User> getTopUsers() {
        ArrayList<User> topUsers = new ArrayList<>();

        for (Integer i : messageDao.findTopUsers()) {
            topUsers.add(userDao.find(i));
        }
        return topUsers;
    }

    public void contenuIntrouvable() {
        MessageDao messageDao = new MessageDao();
        messageDao.delete(messages.get(messages.size() - 1).getId());
        messages.remove(messages.size() - 1);
        JOptionPane.showMessageDialog(homeView, "Image introuvable, veuillez charger votre image sous le bon nom dans le fichier imageEnvoyees", "Erreur de chargement d'image", JOptionPane.ERROR_MESSAGE);
        homeView.repaint();
    }

    public void setUser(User user) {
        this.users.set(user.getId() - 1, user);
    }

    public void setMessage(Message message) {
        this.messages.set(message.getId() - 1, message);
    }

    public Home getHomeView() {
        return homeView;
    }

    public void pageSettings() {
        try {
            settingsView = new Settings();
            //view5.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        settingsView.setVisible(true);
    }

    //----------------------------------STATE-----------------------------------------
    public void newState() {
        switch (this.currentUser.getState()) {
            case ONLINE -> {
                this.currentUser.setState(User.State.AWAY);
                this.homeView.getStatutButton().setText("Away");
                this.homeView.setCircleColor(Color.ORANGE);
            }
            case AWAY -> {
                currentUser.setState(User.State.ONLINE);
                this.homeView.getStatutButton().setText("Online");
                this.homeView.setCircleColor(Color.GREEN);
            }
        }
        sendToServerNewState();
        this.userDao.update(currentUser);
        this.homeView.repaint();
    }

    //-----------------------------CHANGEMENT DE PERMISSION------------------------------
    public void gererFenetresInfos(int i) {
        try {
            infoUserView = new InfoUser(users.get(i), currentUser);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        infoUserView.setVisible(true);
        infoUserView.addAllListener(this);
    }

    public void newRole() {
        User userSelected = infoUserView.getUser();
        String selected = (String) infoUserView.getPermissionBox().getSelectedItem();
        if (selected != null) {
            switch (selected) {
                case "ADMINISTRATOR" ->
                        userSelected.setPermission(User.Permission.ADMINISTRATOR);
                case "MODERATOR" -> userSelected.setPermission(User.Permission.MODERATOR);
                case "USER" -> userSelected.setPermission(User.Permission.USER);
            }
        }
        userDao.update(userSelected);

        sendToServerPermission(userSelected);
    }

    public InfoUser getInfoUserView() {
        return infoUserView;
    }

    //------------------------------LISTENERS------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] actionCommand = e.getActionCommand().split(" ");
        switch (actionCommand[0]) {
            //Gère la connexion
            case "Connexion" -> connection(menuView.getUsername(), menuView.getPassword());

            //Gère la déconnexion graphiquement
            case "logOut" -> gererFenetresLogOut();

            //Gère la déconnexion hors graphique
            case "Disconnection" -> disconnection();

            //Gère le changement de statut
            case "State" -> newState();

            //Gère le bannissement
            case "Ban" -> bannissement(Integer.parseInt(actionCommand[1]));

            //Gère la modification de son mdp
            case "Ok" -> newMdp();

            //Gère l'envoie de message
            case "send" -> send(homeView.getTextField1().getText());

            //Gère l'oublie de mdp
            case "mdpOublie" -> mdpOublie();

            //Gère les stats
            case "Stats" -> pageStats();

            case "SmileyIntrouvable", "ImageIntrouvable" -> contenuIntrouvable();

            case "Settings" -> pageSettings();

            case "Infos" -> gererFenetresInfos(Integer.parseInt(actionCommand[1]));

            case "NewRole" -> newRole();

        }
    }
}
