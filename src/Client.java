import java.io.*;
import java.net.*;
import java.util.*;

// Client class
class Client {

    static String name;
    //static Controller controller;


    Client(String name){
        this.name = name;
    }

    public static void main(String[] args)
    {

        Scanner sc = new Scanner(System.in);
        //ask for a name:
        System.out.println("Enter your Username: ");
        name = sc.nextLine();
        //TODO : Connexion()


        try (Socket socket = new Socket("localhost", 9000)) {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // reading from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // sending the name to the server
            String line = null;

            out.println(name + " has joined the chat");
            out.flush();
            //TODO: Avertir avec view que le client a rejoint le chat

            ThreadToDisplay threadToDisplay = new ThreadToDisplay(in);

            threadToDisplay.start();

            while (!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();
                switch (line){
                    case "exit":
                        threadToDisplay.interrupt();
                        System.out.println("You have left the chat");
                        out.println(name + " has left the chat :(");
                        out.flush();
                        //TODO: Avertir avec view que le client a quitté le chat

                        return;

                    default:
                        out.println(name + ": " + line);
                        out.flush();
                        break;
                }


            }

            // closing the scanner object
            sc.close();

        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}