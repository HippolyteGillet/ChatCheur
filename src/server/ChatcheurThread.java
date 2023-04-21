package server;

import java.io.*;
import java.net.*;

public class ChatcheurThread implements Runnable {
    private final boolean threadRunning = true;
    private Thread thread;
    private ChatcheurServer chatcheurServer;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int numClient = 0;

    public ChatcheurThread(Socket s, ChatcheurServer chatcheurServer) {
        this.clientSocket = s;
        this.chatcheurServer = chatcheurServer;
        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new PrintWriter(clientSocket.getOutputStream());
            this.numClient = chatcheurServer.addClient(this.out);
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
            try {
                System.out.println("Le client no " + numClient + " s'est deconnecte");
                chatcheurServer.deleteClient(numClient);
                clientSocket.close();
            } catch (IOException ignored) {

            }
        }
    }

    public void readCommand() throws IOException {
        String[] clientCommand = this.in.readLine().split(" ");

        if (!threadRunning) {
            System.out.println("Client not running anymore");
        } else if (clientCommand[0].equals("Connection:")) {
            chatcheurServer.sendAllMessage(tableauToMessage(clientCommand));
        } else if (clientCommand[0].equals("Message")) {
            chatcheurServer.sendAllMessage(tableauToMessage(clientCommand));
        }

    }

    public String tableauToMessage(String[] words) {
        String string = "";
        for (String message : words) {
            string += " " + message;
        }
        return string;
    }

}
