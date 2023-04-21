package server;

import controller.ClientController;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientHandler extends Thread{
    private BufferedReader in;
    private ClientController controller;

    public ClientHandler(BufferedReader in, ClientController c) {
        this.controller = c;
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
