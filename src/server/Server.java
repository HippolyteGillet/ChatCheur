package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

// Server.Server class
class Server {

    //Array of all the active clients:
    static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args)
    {
        ServerSocket server = null;

        try {

            server = new ServerSocket(9000);
            server.setReuseAddress(true);

            // running infinite loop for getting
            // client request
            while (true) {

                // socket object to receive incoming client
                // requests
                Socket client =  server.accept();

                // Displaying that new client is connected
                // to server
                System.out.println("Somebody is coming from... ");

                // create a new thread object
                ClientHandler clientSock = new ClientHandler(client);
                clients.add(clientSock);

                // This thread will handle the client
                // separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }




    // ClientHandler class
    public static class ClientHandler implements Runnable {
        private Socket clientSocket;

        // Constructor
        public ClientHandler(Socket socket)
        {
           this.clientSocket = socket;

        }

        public static void sendToAll(String message) {
            for (ClientHandler client : clients) {
                client.sendMessage(message);
            }
        }

        public void sendMessage(String message) {
            try {
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println(message);
                out.flush();
                //TODO: Faire tous les appels nécessaires pour envoyer le message à tous les clients
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        public void run()
        {

            try {

                // get the outputstream of client
                PrintWriter out = new PrintWriter(
                        clientSocket.getOutputStream(), true);

                // get the inputstream of client
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    // writing the received message from
                    // client

                    System.out.println(line);
                    out.println(line);
                    sendToAll(line);

                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                if (clientSocket != null) {
                    try {
                        clientSocket.close();
                        clients.remove(this);
                        //TODO: Faire tous les appels nécessaires pour supprimer le client de la liste des clients connectés
                        //TODO: deconnexion()
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
