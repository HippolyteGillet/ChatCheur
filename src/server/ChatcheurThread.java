package server;

import java.io.*;
import java.net.*;

public class ChatcheurThread implements Runnable {
    private Thread thread;
    private ChatcheurServer chatcheurServer;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int numClient = 0;
    private boolean threadRunning = true;

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
                System.out.println(in.readLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCommand() throws IOException {
        String[] clientCommand = in.readLine().split(" ");

        if (!threadRunning) {
            System.out.println("Server already stopped");
        }
        if (clientCommand[0].equals("Connection:")) {
            chatcheurServer.sendAllMessage(tableauToMessage(clientCommand));
        }

    }

    public String tableauToMessage(String [] words) {
        String string = "";
        for (String message : words) {
            string += message;
        }
        return string;
    }

}
