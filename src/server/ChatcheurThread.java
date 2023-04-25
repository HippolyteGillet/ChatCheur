package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

/**
 * CHATCHEUR THREAD
 * --> Instances of these classes are created by the server when a new client connects to the server.
 */

public class ChatcheurThread implements Runnable {

    /**
     * ATTRIBUTES
     */
    private final ChatcheurServer chatcheurServer;
    private final Socket clientSocket;
    private boolean threadRunning = true;
    private BufferedReader in;
    private int numClient = 0;

    /**
     * CONSTRUCTOR
     */
    public ChatcheurThread(Socket s, ChatcheurServer chatcheurServer) {
        this.clientSocket = s;
        this.chatcheurServer = chatcheurServer;
        try {
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.numClient = chatcheurServer.addClient(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread thread = new Thread(this);
        thread.start();
    }

    /**
     * METHODS
     * <p>
     * Run the thread.
     * Read the command sent by the client.
     * Send the message to the server.
     * Close the socket.<br>
     * <p>
     */
    @Override
    public void run() {

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

    /**
     * Read the command sent by the client.
     */
    public void readCommand() throws IOException {

        //We transform the string into an array of words to be able to sort it
        String[] clientCommand = this.in.readLine().split(" ");

        switch (clientCommand[0]) {
            case "Connection:", "Disconnection:", "Message", "Change:", "Role:" -> this.chatcheurServer.sendAllMessage(arrayToMessage(clientCommand));
            case "End" ->  clientSocket.close();
            default -> System.out.println("default");
        }
    }

    /**
     * Transform an array of words into a string.
     */
    public String arrayToMessage(String[] words) {

        StringBuilder string = new StringBuilder();
        for (String message : words) {
            string.append(message).append(" ");
        }
        return string.toString();
    }

}
