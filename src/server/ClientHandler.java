package server;

import controller.ClientController;
import model.Message;
import model.user.Administrator;
import model.user.Moderator;
import model.user.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private BufferedReader in;
    private ClientController controller;

    public ClientHandler(BufferedReader in, ClientController c) {
        this.controller = c;
        this.in = in;
    }

    public void run() {
        try {
            while (!this.isInterrupted()) {
                readCommand();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void readCommand() throws IOException {
        String[] clientCommand = this.in.readLine().split(" ");

        switch (clientCommand[0]) {
            case "Connection:", "Disconnection:"  -> userUpdate(User.convertionMessageIntoUser(clientCommand));

            case "Change:" -> tabUsersUpdate(clientCommand);

            case "Role:" -> roleUpdate(clientCommand);

            case "Message" -> messageReceived(User.convertionMessageIntoMessage(clientCommand));

            default -> System.out.println("default");
    }

}

    public void roleUpdate(String[] clientCommand) {
        String newUser[] = new String[10];
        for (int i = 1; i < 11; i++) {
            String temp = clientCommand[i].split("=")[1];
            newUser[i-1] = temp.substring(0, temp.length()-1);
        }
        switch (newUser[6]){
            case "ADMINISTRATOR" -> controller.setUser(new Administrator(Integer.parseInt(newUser[0]), newUser[1], newUser[2], newUser[3], newUser[4], newUser[5], User.State.valueOf(newUser[8]), LocalDateTime.now()));
            case "MODERATOR" -> controller.setUser(new Moderator(Integer.parseInt(newUser[0]), newUser[1], newUser[2], newUser[3], newUser[4], newUser[5], User.State.valueOf(newUser[8]), LocalDateTime.now()));
            case "USER" -> controller.setUser(new User(Integer.parseInt(newUser[0]), newUser[1], newUser[2], newUser[3], newUser[4], newUser[5], User.State.valueOf(newUser[8]), LocalDateTime.now()));
        }
        LocalDateTime date = LocalDateTime.parse(newUser[9]);
        controller.getUsers().get(Integer.parseInt(newUser[0])-1).setLastConnectionTime(date);
        if (controller.getCurrentUser().getId() == (Integer.parseInt(newUser[0]))) {
            controller.setCurrentUser(controller.getUsers().get(Integer.parseInt(newUser[0])-1));
        }
        this.controller.getView2().repaint();
    }

    public void tabUsersUpdate(String[] clientCommand) {
        String newUser[] = new String[10];
        for (int i = 1; i < 11; i++) {
            String temp = clientCommand[i].split("=")[1];
            newUser[i-1] = temp.substring(0, temp.length()-1);
        }
        controller.getUsers().get(Integer.parseInt(newUser[0])-1).setUserName(newUser[1]);
        controller.getUsers().get(Integer.parseInt(newUser[0])-1).setPassword(newUser[2]);
        controller.getUsers().get(Integer.parseInt(newUser[0])-1).setState(User.State.valueOf(newUser[8]));
        controller.getUsers().get(Integer.parseInt(newUser[0])-1).setAccess(User.Access.valueOf(newUser[7]));

        if (controller.getCurrentUser() != null) {
            if (controller.getCurrentUser().getId() == (Integer.parseInt(newUser[0])) && newUser[7].equals("BANNED")) {
                controller.getCurrentUser().setAccess(User.Access.BANNED);
                controller.disconnection(false);

            }
        }
        if (this.controller.getView2() != null) {
            this.controller.getView2().repaint();
        }

    }

    public void userUpdate(User user) {
        this.controller.setUser(user);
        if (this.controller.getView2() != null) {
            this.controller.getView2().repaint();
        }
    }

    public void messageReceived(Message message) {
        this.controller.getMessages().add(message);
        if (this.controller.getView2() != null) {
            this.controller.getView2().repaint();
        }
    }
}
