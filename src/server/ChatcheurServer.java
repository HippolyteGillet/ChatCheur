package server;

import DAO.LogDao;
import DAO.MessageDao;
import DAO.UserDao;
import model.Log;
import model.user.User;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

// Server class
class ChatcheurServer {

    private final UserDao userDao = new UserDao();
    private final LogDao logDao = new LogDao();
    private final MessageDao messageDao = new MessageDao();
    //Array of all the active clients:
    private ArrayList<PrintWriter> clients = new ArrayList<>();

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
        PrintWriter out = (PrintWriter) client;
        if (out != null) {
            out.println(message);
        }
    }

    synchronized public void sendAllMessage(String message) {
        for (PrintWriter client : clients) {
            client.println(message);
        }
    }

    synchronized public int nbclients() {
        return clients.size();
    }

}