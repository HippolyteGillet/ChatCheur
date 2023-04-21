package server;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientHandler extends Thread{
    BufferedReader in;

    public ClientHandler(BufferedReader in) {
        this.in = in;
    }

    public void run(){
        while (!Thread.currentThread().isInterrupted()){
            try {
                System.out.println(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
