package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// Server class
class ChatcheurServer {

    //Array of all the active clients:
    private ArrayList<PrintWriter> clients = new ArrayList<>();

    public static void main(String[] args) {
        ChatcheurServer chatcheurServer = new ChatcheurServer();
        ServerSocket serverSocket = null;
        int portNumber = 8999;

        try {
            serverSocket = new ServerSocket(portNumber);
            System.out.println("New Server has started listening on port: " + portNumber);
            serverSocket.setReuseAddress(true);
        } catch (IOException e) {
            System.out.println("Cannot listen on port: " + portNumber + ", Exception: " + e);
            System.exit(1);
        }

        //Lance le thread de gestion des commandes
        //new Commands(chatcheurServer);

        //Attente en boucle de nouvelles connexions
        while (true) {
            Socket clientSocket = null;
            try {
                System.out.println("Listening for a connection...");

                //Ouvertue socket server sur port
                clientSocket = serverSocket.accept();
                System.out.println("Accepted socket connection from  client ");

                //Lance le thread du client
                new ChatcheurThread(clientSocket, chatcheurServer);
                // This thread will handle the client
                // separately
            } catch (IOException e) {
                System.out.println("XX. Accept failed: " + portNumber + e);
            }
        }
    }

    synchronized public int addClient(PrintWriter out) {
        clients.add(out);
        return clients.size() - 1;
    }

    synchronized public void deleteClient(int i) {
        if (clients.get(i) != null) {
            //On supprime client
            clients.remove(i);
        }
    }

    synchronized public void sendMessage(String message, PrintWriter client) {
        PrintWriter out = new PrintWriter(client, true);
        out.println(message);
        out.flush();
    }

    synchronized public void sendAllMessage(String message) {
        for (PrintWriter client : clients) {
            sendMessage(message, client);
        }
    }

    synchronized public int nbclients() {
        return clients.size();
    }
}