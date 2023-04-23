package server;

import controller.ClientController;
import model.Message;
import model.user.User;

import java.io.BufferedReader;
import java.io.IOException;

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

    public void readCommand() throws IOException {
        String[] clientCommand = this.in.readLine().split(" ");

        switch (clientCommand[0]) {
            case "Connection:", "Disconnection:", "Kick:", "State:", "Permission:" -> userUpdate(User.convertionMessageIntoUser(clientCommand));

            case "Message" -> messageReceived(User.convertionMessageIntoMessage(clientCommand));

            default -> System.out.println("default");
    }

}

    public void userUpdate(User user) {
        this.controller.setUser(user);
        if (this.controller.getHomeView() != null) {
            this.controller.getHomeView().repaint();
        }
        if (this.controller.getInfoUserView() != null) {
            this.controller.getInfoUserView().repaint();
        }
    }

    public void messageReceived(Message message) {
        this.controller.getMessages().add(message);
        if (this.controller.getHomeView() != null) {
            this.controller.getHomeView().repaint();
        }
    }
}
