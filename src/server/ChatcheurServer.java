package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

/**
 * CHATCHEUR SERVER
 * --> Instances of these classes are created by the server when a new client connects to the server.
 */
public class ChatcheurServer {
    /**
     * ArrayList of all the clients connected to the server.
     */
    private final ArrayList<PrintWriter> clients = new ArrayList<>();

    /**
     * Add a client to the ArrayList of clients.
     * Create a new PrintWriter for the client.
     * Send the message to the client.
     * Create a new thread for the client.
     */
    public static void main(String[] args) {

        ChatcheurServer chatcheurServer = new ChatcheurServer();
        ServerSocket serverSocket = null;
        int portNumber = 8999;

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("New Server has started listening on port: " + portNumber);

        } catch (IOException e) {
            System.out.println("Cannot listen on port: " + portNumber + ", Exception: " + e);
            System.exit(1);
        }

        while (true) {
            Socket clientSocket;
            try {
                System.out.println("Listening for a connection...");

                // Accept incoming connections.
                clientSocket = serverSocket.accept();
                System.out.println("Accepted socket connection from  client ");

                // Create a new thread for each connection.
                new ChatcheurThread(clientSocket, chatcheurServer);

            } catch (IOException e) {
                System.out.println("XX. Accept failed: " + portNumber + e);
            }
        }
    }

    /**
     * METHODS
     */
    synchronized public int addClient(PrintWriter out) {
        //On ajoute un client à la liste du serveur
        clients.add(out);
        return clients.size() - 1;
    }

    synchronized public void deleteClient(int i) {
        //on supprime le client de la liste du Serveur
        if (clients.get(i) != null) {
            clients.remove(i);
        }
    }

    synchronized public void sendAllMessage(String message) {
        //on envoie le message à tous les clients du serveur via le Printer de chacun
        for (PrintWriter client : clients) {
            client.println(message);
        }
    }

}