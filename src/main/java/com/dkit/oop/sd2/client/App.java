package com.dkit.oop.sd2.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        App client = new App();
        client.start();
    }

    public void start()
    {
        Scanner in = new Scanner(System.in);
        try {
            Socket socket = new Socket("localhost", 8080);  // connect to server socket
            System.out.println("Client: Port# of this client : " + socket.getLocalPort());
            System.out.println("Client: Port# of Server :" + socket.getPort() );

            System.out.println("Client message: The Client is running and has connected to the server");

            System.out.println("Please enter a command:  (\"Time\" to get time, or \"Echo message\" to get echo) \n>");
            String command = in.nextLine();

            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);   // true => auto flush buffers

            socketWriter.println(command);

            Scanner socketReader = new Scanner(socket.getInputStream());  // wait for, and retrieve the reply

            if(command.startsWith("Time")) {
                String timeString = socketReader.nextLine();
                System.out.println("Client message: Response from server Time: " + timeString);
            } else {
                String input = socketReader.nextLine();
                System.out.println("Client message: Response from server: \"" + input + "\"");
            }

            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: "+e);
        }
    }
}