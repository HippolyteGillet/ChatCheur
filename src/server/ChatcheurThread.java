package server;

import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import controller.ClientController;

import java.io.*;
import java.net.*;

public class ChatcheurThread implements Runnable {
    private boolean threadRunning = true;
    private final ChatcheurServer chatcheurServer;
    private final Socket clientSocket;
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
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Le client no " + this.numClient + " s'est deconnecte");
            this.chatcheurServer.deleteClient(this.numClient);

        }
    }

    public void readCommand() throws IOException {
        String[] clientCommand = this.in.readLine().split(" ");

        switch (clientCommand[0]) {
            case "Connection:" -> {
                this.chatcheurServer.sendAllMessage(tableauToMessage(clientCommand));
                this.chatcheurServer.connection(clientCommand);
            }
            case "Message" -> {
                this.chatcheurServer.sendAllMessage(tableauToMessage(clientCommand));
                /*try {

                    messageDao.create(clientCommand[4]);
                    logDao.create(logToSend);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }
            case "Disconnection:" -> {
                this.chatcheurServer.sendAllMessage(tableauToMessage(clientCommand));
                this.threadRunning = false;
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
