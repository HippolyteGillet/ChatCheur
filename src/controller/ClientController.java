package controller;

import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import model.Log;
import model.Message;
import model.user.User;
import view.Menu;
import view.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClientController implements ActionListener {

    private final LogDao logDao = new LogDao();
    private final MessageDao messageDao = new MessageDao();
    private final UserDao userDao = new UserDao();
    private Menu view1;
    private Home view2;
    private LogOut view3;
    private NewPassword view4;
    private Stats view5;
    private Settings view6;
    private InfoUser view7;
    private User currentUser;
    private List<User> users;
    private List<Log> logs;
    private List<Message> messages;
    private PrintWriter out;
    private Color C1, C2, C3, C4, C5, C6;

    /**
     * Builer for the class
     * @param users list of user from the DB
     * @param logs list of logs from the DB
     * @param messages lsit of messages from the DB
     * @param view the view to control
     * @param socket the socket of the server
     */
    public ClientController(List<User> users, List<Log> logs, List<Message> messages, Menu view, Socket socket) {
        this.currentUser = null;
        this.users = users;
        this.logs = logs;
        this.messages = messages;
        this.view1 = view;
        this.view2 = null;
        this.view3 = null;
        this.view4 = null;
        this.view5 = null;
        this.view6 = null;
        this.view7 = null;
        view1.addAllListener(this);
        try {
            this.out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This function cost a string input
     * @param input password to be costed
     * @return the costed password
     */
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

    //--------------------------------GETTER AND SETTER BASICS------------------
    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public User getCurrentUser() {
        return currentUser;
    }


    //--------------------------CONNECTION-------------------------------

    /**
     * This function search for a matching username and password and does all the actions
     * related to the connection like managing the windows and telling the server the connection
     * @param username username of the user in the DB
     * @param psw password of the user in the DB
     */
    public void connection(String username, String psw) {
        boolean userFinded = false;
        //On parcourt tous les users
        for (User user : this.users) {
            //On cherche un user avec le nom et le mdp correspondent
            if (user.getUserName().equals(username)) {
                if (user.getUserName().equals(username) && user.getPassword().equals(sha256(psw))) {
                    userFinded = true;
                    System.out.println("User trouve : " + username);
                    //On regarde si le user est banni
                    if (user.getAccess().equals(User.Access.ACCEPTED)) {
                        if (!user.getState().equals(User.State.ONLINE)) {
                            this.currentUser = user;
                            this.currentUser.setState(User.State.ONLINE);
                            this.currentUser.setLastConnectionTime(LocalDateTime.now());

                            gererFenetresConnection();
                            sendToServerConnection();
                            connectionToDB(this.currentUser);
                        } else {
                            JOptionPane.showMessageDialog(null, "Vous êtes déjà connecté sur un autre appareil");
                        }
                    } else {
                        System.out.println("Connexion refusee, le user est banni");
                        view1.afficherBannissement();
                    }
                } else if (user.getUserName().equals(username)) {
                    userFinded = true;
                    System.out.println("Mdp incorrect");
                    view1.afficherMdpIncorrect();
                }
            }
        }
        if (this.currentUser == null && !userFinded) {
            System.out.println("Aucun utilisateur trouve");
            view1.afficherUserUknown();
        }
        //view2.repaint();
    }

    /**
     * This function send to the DB the connection of the user
     * @param user the user connected
     */
    public void connectionToDB(User user) {
        //On met a jour BDD
        this.userDao.update(user);
        //Création d'un log connection
        Log logConnection = new Log(user.getId(), Log.TypeLog.CONNECTION);
        //On ajoute le log dans la BDD
        logDao.create(logConnection);
    }

    /**
     * This function manages windows once the connection is established
     */
    public void gererFenetresConnection() {
        //On crée fenetre
        try {
            //On supprime menu
            this.view1.dispose();
            this.view2 = new Home(users, messages, view1.getUsername(), C1, C2, C3, C4, C5, C6);
            //On met la 1ere fenetre a null
            this.view1 = null;
            this.view2.addAllListener(this);
            this.view2.setVisible(true);
            this.view2.setResizable(true);
            this.view2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.view2.setLocationRelativeTo(null);
            view2.repaint();
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    //-----------------------------MESSAGE---------------------------------

    /**
     * This function send the message to the server and update the view
     * @param message the new message to send
     */
    public void send(String message) {
        if (view2.getTextField().getText().contains("'")) {
            message = message.replace("'", "‘");
        }
        if (!message.equals("Saisir du texte") && !message.isEmpty() && currentUser != null) {
            Message messagToSend = new Message(currentUser.getId(), message, messageDao.getLastID() + 1);
            Log logToSend = new Log(currentUser.getId(), Log.TypeLog.MESSAGE);
            //On met a jour la vue
            messages.add(messagToSend);
            int y = view2.calculY(messages);
            if (message.charAt(0) == '/') {
                ImageIcon imageIcon = new ImageIcon("imageEnvoyees" + message);
                int imageIconWidth = imageIcon.getIconWidth();
                int imageIconHeight = imageIcon.getIconHeight();
                double ratio = (double) imageIconWidth / (double) imageIconHeight;
                if (imageIconWidth > 300) {
                    imageIconWidth = 500;
                    imageIconHeight = (int) (imageIconWidth / ratio);
                }
                if (imageIconHeight > 300) {
                    imageIconHeight = 300;
                }
                y -= imageIconHeight + 70;
            }
            messages.remove(messagToSend);
            view2.getScrollPane().getVerticalScrollBar().setValue(view2.getScrollPane().getVerticalScrollBar().getMaximum());
            view2.getconversationPanelContent().setPreferredSize(new Dimension(950, y + 60));
            view2.getScrollPane().getViewport().setViewPosition(new Point(0, y));
            view2.setY(y);
            view2.getTextField().setText(null);
            view2.repaint();

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

    /**
     * This function disconnect the user from the client does all the actions
     * related to the disconnection
     * @param sendToServer if true it will send the disconnection to the server
     */
    public void disconnection(boolean sendToServer) {
        this.currentUser.setState(User.State.OFFLINE);
        if (sendToServer) sendToServerDisconnection();
        Log logDeconnection = new Log(currentUser.getId(), Log.TypeLog.DISCONNECTION);
        //On met a jour BDD
        logDao.create(logDeconnection);
        this.userDao.update(currentUser);
        this.currentUser = null;
        gererFenetresDisconnection();
    }

    /**
     * This function manages windows once the disconnection is established
     */
    public void gererFenetresDisconnection() {
        //On ferme les autres fenetres
        if (view3 != null) {
            view3.dispose();
            view3 = null;
        }
        view2.dispose();
        view2 = null;
        //On crée la fenetre de base
        try {
            view1 = new Menu(users, logs, messages, C1, C2, C3, C4, C5, C6);
            view1.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * This function manages windows before the disconnection is established
     */
    public void gererFenetresLogOut() {
        view3 = new LogOut(view2, C1, C2, C3, C4, C5, C6);
        view3.setVisible(true);
        view3.addAllListener(this);
    }

    public void infoUser(int i) {
        try {
            view7 = new InfoUser(users.get(i - 1), currentUser, C1, C2, C3, C4, C5, C6);
            view7.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        view7.setVisible(true);
    }

    //----------------------------------BANNISSEMENT-----------------------------------------

    /**
     * This function ban a user, update the view and send to the server the kick
     * @param i represent the user to ban
     */
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

        //Si l'utilisateur est banni, on le débanni, sinon on le banni
        if (userToChange.getAccess().equals(User.Access.BANNED)) {
            int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir débannir cet utilisateur ?", "Confirmer le débannissement", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                //A trouver une solution pour le i
                //view2.setIconBan(positionIcon);
                for (User userJava : users) {
                    if (userJava.getId() == userToChange.getId()) {
                        userJava.setAccess(User.Access.ACCEPTED);
                    }
                }
                view2.repaint();
                userDao.update(userToChange);
                Log logBan = new Log(userToChange.getId(), Log.TypeLog.UNBAN);
                logDao.create(logBan);
            }
        } else {
            int response = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir bannir cet utilisateur ?", "Confirmer le bannissement", JOptionPane.YES_NO_OPTION);
            if (response == JOptionPane.YES_OPTION) {
                //view2.setIconUnban(positionIcon);
                for (User userJava : users) {
                    if (userJava.getId() == userToChange.getId()) {
                        userJava.setAccess(User.Access.BANNED);
                    }
                }
                view2.repaint();

                userDao.update(userToChange);
                Log logBan = new Log(userToChange.getId(), Log.TypeLog.BAN);
                logDao.create(logBan);
            }
        }
        sendToServerChange(userToChange);
    }

    //-----------------------------------ENVOIE SERVEUR-----------------------------------------

    /**
     * This function send the connection of the current user to the server
     */
    public void sendToServerConnection() {
        this.out.println("Connection: " + this.currentUser.getUserName() + " connected to server " + this.currentUser);
    }

    /**
     * This function send a ban to the server
     */
    public void sendToServerChange(User userChanged) {
        this.out.println("Change: " + userChanged);
    }

    /**
     * This function send the change of role of a user to the server
     */
    public void sendToServerRole(User userChanged) {
        this.out.println("Role: " + userChanged);
    }

    /**
     * This function send a message of the current user to the server
     */
    public void sendToServerMessage(Message message) {
        try {
            this.out.println("Message de " + this.currentUser.getUserName() + " : " + message);
        } catch (Exception e) {
            System.out.println("ERROR, Exception occured on sending");
        }
    }

    /**
     * This function send the disconnection of the current user to the server
     */
    public void sendToServerDisconnection() {
        this.out.println("Disconnection: " + currentUser.getUserName() + " disconnected from server " + this.currentUser);
    }

    /**
     * This function send end to the server
     */
    public void sendToServeurEnd() {
        this.out.println("End");
    }

    //-------------------------------------PASSWORD-------------------------------------------

    /**
     * This function allows the user to change his password
     */
    public void mdpOublie() {
        try {
            view4 = new NewPassword(C1, C2, C3, C4, C5, C6);
            view4.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        view4.setVisible(true);
    }

    /**
     * This function change the password of the current user
     */
    public void newMdp() {
        boolean userExist = false;
        for (User user : users) {
            if (user.getUserName().equals(view4.getTextFieldUserName())) {
                userExist = true;
            }
        }
        if (userExist) {
            if (Objects.equals(view4.getTextFieldNewPassword(), view4.getTextFieldConfirmPassword())) {
                currentUser = userDao.findUserName(view4.getTextFieldUserName());
                currentUser.setPassword(sha256(view4.getTextFieldNewPassword()));
                userDao.update(currentUser);
                sendToServerChange(currentUser);
                currentUser = null;
                view4.dispose();
                view4 = null;
            } else {
                JOptionPane.showMessageDialog(null, "Les mots de passe ne correspondent pas", "Erreur", JOptionPane.ERROR_MESSAGE);
                view4.setTextFieldNewPassword("");
                view4.setTextFieldConfirmPassword("");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez entrer un User existant", "Erreur", JOptionPane.ERROR_MESSAGE);
            view4.setTextFieldUserName("");
            view4.setTextFieldNewPassword("");
            view4.setTextFieldConfirmPassword("");
        }

    }

    //-----------------------------------STATS------------------------------------------------

    /**
     * This function generates stats related to the user
     */
    public void pageStats() {
        try {
            view5 = new Stats(getTypeUser(), getTypeModerator(), getTypeAdministrator(),
                    getUsersOnline(), getUsersAway(), getUsersOffline(),
                    getNumberBanned(), getNumberMessagesPerHour(), getNumberConnectionsPerHour(), getTopUsers(),
                    C1, C2, C3, C4, C5, C6);
            //view5.addAllListener(this);

        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        view5.setVisible(true);

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
        JOptionPane.showMessageDialog(view2, "Image introuvable, veuillez charger votre image sous le bon nom dans le fichier imageEnvoyees", "Erreur de chargement d'image", JOptionPane.ERROR_MESSAGE);
        view2.repaint();
    }

    public void setUser(User user) {
        this.users.set(user.getId() - 1, user);
    }

    public void setMessage(Message message) {
        this.messages.set(message.getId() - 1, message);
    }

    public Home getView2() {
        return view2;
    }

    /**
     * This function manages the windows related to the settings
     */
    public void pageSettings() {
        try {
            view6 = new Settings(C1, C2, C3, C4, C5, C6);
            view6.addAllListener(this);
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        view6.setVisible(true);
    }

    /**
     * This function changes the username of the current user
     */
    public void changeUsn() {
        if (!Objects.equals(view6.getTextField1().getText(), "")) {
            currentUser.setUserName(view6.getTextField1().getText());
            userDao.update(currentUser);
            sendToServerChange(currentUser);
            view6.dispose();
            view6 = null;
            view2.repaint();
        }
    }

    /**
     * This function changes the password of the current user
     */
    public void changePsswrd() {
        if (!Objects.equals(view6.getTextField2().getText(), "")) {
            currentUser.setPassword(sha256(view6.getTextField2().getText()));
            userDao.update(currentUser);
            sendToServerChange(currentUser);
            view6.dispose();
            view6 = null;
        } else {
            JOptionPane.showMessageDialog(view6, "Veuillez entrer un mot de passe", "Erreur de changement de mot de passe", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void resetColors() {
        C1 = new Color(246, 245, 254);
        C2 = new Color(251, 108, 122);
        C3 = new Color(21, 21, 21);
        C4 = new Color(111, 35, 255);
        C5 = new Color(128, 101, 254);
        C6 = Color.BLACK;
        view2.dispose();
        try {
            view2 = new Home(users, messages, currentUser.getUserName(), C1, C2, C3, C4, C5, C6);
            view2.addAllListener(this);
            view2.setVisible(true);
            view2.setResizable(true);
            view2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view2.setLocationRelativeTo(null);
            view2.repaint();
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        view6.dispose();
        pageSettings();
    }

    public void initialColors() {
        if (C1 == null && C2 == null && C3 == null && C4 == null && C5 == null && C6 == null) {
            C1 = new Color(246, 245, 254);
            C2 = new Color(251, 108, 122);
            C3 = new Color(21, 21, 21);
            C4 = new Color(111, 35, 255);
            C5 = new Color(128, 101, 254);
            C6 = Color.BLACK;
        }
    }

    /**
     * This function changes the colors of the client
     */
    public void setTheme1() {
        C1 = new Color(244, 225, 214);
        C2 = new Color(128, 14, 35);
        C3 = new Color(20, 38, 46);
        C4 = new Color(37, 122, 134);
        C5 = new Color(63, 168, 158);
        C6 = Color.BLACK;
        view2.dispose();
        try {
            view2 = new Home(users, messages, currentUser.getUserName(), C1, C2, C3, C4, C5, C6);
            view2.addAllListener(this);
            view2.setVisible(true);
            view2.setResizable(true);
            view2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view2.setLocationRelativeTo(null);
            view2.repaint();
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        view6.dispose();
        pageSettings();
    }

    /**
     * This function changes the colors of the client
     */
    public void setTheme2() {
        C1 = new Color(254, 232, 199);
        C2 = new Color(20, 79, 89);
        C3 = new Color(1, 17, 27);
        C4 = new Color(100, 29, 14);
        C5 = new Color(251, 102, 9);
        C6 = Color.BLACK;
        view2.dispose();
        try {
            view2 = new Home(users, messages, currentUser.getUserName(), C1, C2, C3, C4, C5, C6);
            view2.addAllListener(this);
            view2.setVisible(true);
            view2.setResizable(true);
            view2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view2.setLocationRelativeTo(null);
            view2.repaint();
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        view6.dispose();
        pageSettings();
    }

    /**
     * This function changes the colors of the client
     */
    public void setTheme3() {
        C1 = new Color(255, 246, 236);
        C2 = new Color(248, 146, 17);
        C3 = Color.BLACK;
        C4 = new Color(17, 24, 46);
        C5 = new Color(102, 133, 202);
        C6 = Color.BLACK;
        view2.dispose();
        try {
            view2 = new Home(users, messages, currentUser.getUserName(), C1, C2, C3, C4, C5, C6);
            view2.addAllListener(this);
            view2.setVisible(true);
            view2.setResizable(true);
            view2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            view2.setLocationRelativeTo(null);
            view2.repaint();
        } catch (IOException | FontFormatException ex) {
            throw new RuntimeException(ex);
        }
        view6.dispose();
        pageSettings();
    }

    /**
     * This function changes the colors of the client
     */
    public void addImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif"));
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            File destination = new File("imageEnvoyees/" + file.getName());
            try {
                Files.copy(file.toPath(), destination.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            send("/" + file.getName());
            view2.getTextField().setForeground(Color.BLACK);
        }
    }

    /**
     * This function changes the role of the user selected
     */
    public void newRole() {
        String selected = (String) view7.getComboBox().getSelectedItem();
        User user = users.get(view7.getSelectedUser().getId() - 1);
        if (selected != null) {
            switch (selected) {
                case "ADMINISTRATOR" -> user.setPermission(User.Permission.ADMINISTRATOR);
                case "MODERATOR" -> user.setPermission(User.Permission.MODERATOR);
                case "USER" -> user.setPermission(User.Permission.USER);
            }
        }
        sendToServerRole(user);
        userDao.update(user);
    }

    /**
     * This function change the status of the current user
     */
    public void changeUserStatus() {
        switch (currentUser.getState()) {
            case ONLINE -> {
                currentUser.setState(User.State.AWAY);
                view2.getStatusCurrent().setText("Away");
                view2.setColorStatusCurrent(Color.ORANGE);
            }
            case AWAY -> {
                currentUser.setState(User.State.ONLINE);
                view2.getStatusCurrent().setText("Online");
                view2.setColorStatusCurrent(Color.GREEN);
            }
        }
        userDao.update(currentUser);
        sendToServerChange(currentUser);
        view2.getStatusCurrent().setContentAreaFilled(false);
        view2.getStatusCurrent().setBorder(null);
        view2.getStatusCurrent().setForeground(Color.WHITE);
        view2.getStatusCurrent().setOpaque(false);
        view2.getStatusCurrent().setFocusable(false);
        view2.repaint();
    }

    public void closing() {
        currentUser.setState(User.State.AWAY);
        userDao.update(currentUser);
        view2.dispose();
        sendToServerChange(currentUser);
        sendToServeurEnd();
        // Fermer la fenêtre
    }

    //------------------------------LISTENERS------------------------------------------

    /**
     * This function implements the listener for the different buttons of the view
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] actionCommand = e.getActionCommand().split(" ");
        switch (actionCommand[0]) {
            //Gère la connexion
            case "Connexion" -> {
                initialColors();
                connection(view1.getUsername(), view1.getPassword());
            }

            //Gère la déconnexion graphiquement
            case "logOut" -> {
                initialColors();
                gererFenetresLogOut();
            }

            //Gère la déconnexion hors graphique
            case "Disconnection" -> disconnection(true);

            //Gère le bannissement
            case "Ban" -> bannissement(Integer.parseInt(actionCommand[1]));

            case "Info" -> infoUser(Integer.parseInt(actionCommand[1]));

            //Gère la modification de son mdp
            case "Ok" -> newMdp();

            //Gère l'envoie de message
            case "send" -> send(view2.getTextField().getText());

            //Gère l'oublie de mdp
            case "mdpOublie" -> {
                initialColors();
                mdpOublie();
            }

            //Gère les stats
            case "Stats" -> {
                initialColors();
                pageStats();
            }

            case "SmileyIntrouvable", "ImageIntrouvable" -> contenuIntrouvable();

            case "Settings" -> {
                initialColors();
                pageSettings();
            }

            case "changeUsername" -> changeUsn();

            case "changePassword" -> changePsswrd();

            case "addImage" -> addImage();

            case "newRole" -> newRole();

            case "changeUserStatus" -> changeUserStatus();

            case "closing" -> closing();

            case "theme1" -> setTheme1();

            case "theme2" -> setTheme2();

            case "theme3" -> setTheme3();

            case "reset" -> resetColors();
        }
    }
}
