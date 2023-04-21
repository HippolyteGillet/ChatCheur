package server;

import controller.ClientController;
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
            while (!Thread.currentThread().isInterrupted()) {
                readCommand();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCommand() throws IOException {
        String[] clientCommand = this.in.readLine().split(" ");

        switch (clientCommand[0]) {
            case "Connection:" -> {
                System.out.println("");
                controller.setUser(User.convertionMessageIntoUser(clientCommand));
                controller.getView2().repaint();
            }
            case "Message" -> {
            }
            case "Disconnection:" -> {

            }
            default -> System.out.println("default");
        }
    }
}
