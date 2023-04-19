package server;

import java.io.*;
import java.net.*;

public class ChatcheurThread implements Runnable {
    private Thread thread;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private int numClient = 0;

    private boolean threadRunning = true;

    public ChatcheurThread(Socket s) {
        this.clientSocket = s;

        //on démarre le thread
        thread = new Thread(this);
        thread.start();
    }

    public void run() {
        //On indique dans la console qu'un nouveau client est connecte
        System.out.println("New client connected, num " + numClient);

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream());
            while (threadRunning) {
                readCommand();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readCommand() throws IOException {
        String clientCommand = in.readLine();

        if (!threadRunning) {
            System.out.println("Server already stopped");
        }

    }

}
