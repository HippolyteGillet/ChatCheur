package server;

import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import controller.ClientController;

import java.io.*;
import java.net.*;
import java.util.IllegalFormatCodePointException;

public class ChatcheurThread implements Runnable {
    private final ChatcheurServer chatcheurServer;
    private final Socket clientSocket;
    private boolean threadRunning = true;
    private PrintWriter out;
    private BufferedReader in;
    private int numClient = 0;
    private Thread thread;
    private LogDao logDao = new LogDao();
    private MessageDao messageDao = new MessageDao();
    private UserDao userDao = new UserDao();

    public ChatcheurThread(Socket s, ChatcheurServer chatcheurServer) {
        this.clientSocket = s;
        this.chatcheurServer = chatcheurServer;
        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.numClient = chatcheurServer.addClient(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //on démarre le thread
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        //On indique dans la console qu'un nouveau client est connecte
        System.out.println("New client connected, num " + numClient);

        try {
            while (threadRunning) {
                readCommand();
            }
        } catch (SocketException e) {
            threadRunning = false;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("Le client no " + this.numClient + " s'est deconnecte");
            this.chatcheurServer.deleteClient(this.numClient);
        }
    }

    public void readCommand() throws IOException {
        String[] clientCommand = this.in.readLine().split(" ");

        switch (clientCommand[0]) {
            case "Connection:", "Disconnection:", "Message", "Change:", "Role:" ->
                    this.chatcheurServer.sendAllMessage(tableauToMessage(clientCommand));
            case "End" -> {
                clientSocket.close();
            }
            default -> System.out.println("default");
        }
    }

    public String tableauToMessage(String[] words) {
        String string = "";
        for (String message : words) {
            string += message + " ";
        }
        return string;
    }

}
