package com.dkit.oop.sd2.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.dkit.oop.sd2.core.CAOService;
import com.dkit.oop.sd2.server.DTOs.Course;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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
            OutputStream os = socket.getOutputStream();
            PrintWriter socketWriter = new PrintWriter(os, true);
            Scanner socketReader = new Scanner(socket.getInputStream());;

            System.out.println("Client message: The Client is running and has connected to the server");
            boolean run_outer = true;
            String regex;
            while (run_outer) {
                System.out.println("Please enter a command: ");
                System.out.println("0. Quit");
                System.out.println("1. Login");
                System.out.println("2. Register");
                int command = in.nextInt();
                String request = "";
                int caoNumber;
                String dateOfBirth, password;

                if (command == 0) {
                    break;
                } else if (command == 1) {
                    System.out.println("Enter cao number: ");
                    caoNumber = in.nextInt();
                    System.out.println("Enter date of birth");
                    regex = "^[0-9-]*$";
                    dateOfBirth = in.next();
                    if (!Pattern.matches(regex, dateOfBirth)) {
                        throw new IllegalArgumentException("Only enter number and dashes");
                    }
                    System.out.println("Enter password");
                    password = in.next();
                    request = CAOService.LOGIN + CAOService.BREAKING_CHARACTER + caoNumber + CAOService.BREAKING_CHARACTER + dateOfBirth + CAOService.BREAKING_CHARACTER + password + '\n';
                } else if (command == 2) {
                    System.out.println("Enter cao number: ");
                    caoNumber = in.nextInt();
                    System.out.println("Enter date of birth");
                    dateOfBirth = in.next();
                    regex = "^[0-9-]*$";
                    if (!Pattern.matches(regex, dateOfBirth)) {
                        throw new IllegalArgumentException("Only enter number and dashes");
                    }
                    System.out.println("Enter password");
                    password = in.next();
                    request = CAOService.REGISTER + CAOService.BREAKING_CHARACTER + caoNumber + CAOService.BREAKING_CHARACTER + dateOfBirth + CAOService.BREAKING_CHARACTER + password + '\n';
                } else {
                    throw new IllegalArgumentException("Wrong input");
                }

                socketWriter.println(request);

                String response;
                String[] response_split;
                boolean loggedIn = false;
                if(command == 1) {
                    if (socketReader.nextLine().equals(CAOService.LOGGED_IN)) {
                        loggedIn = true;
                    }
                } else if (socketReader.nextLine().equals(CAOService.REGISTERED)) {
                    loggedIn = true;
                }

                if (loggedIn) {
                    boolean run = true;
                    Gson gson = new Gson();
                    while (run) {
                        System.out.println("Enter command: ");
                        System.out.println("0. Quit");
                        System.out.println("1. Logout");
                        System.out.println("2. Display course");
                        System.out.println("3. Display all courses");
                        System.out.println("4. Display current choices");
                        System.out.println("5. Update current choices");
                        command = in.nextInt();
                        String courseId;
                        switch (command) {
                            case 0:
                                run = false;
                                run_outer = false;
                                break;
                            case 1:
                                request = CAOService.LOGOUT + '\n';
                                socketWriter.println(request);
                                socketReader.nextLine();
                                run = false;
                                break;
                            case 2:
                                System.out.println("Enter course id");
                                courseId = in.next();
                                request = CAOService.DISPLAY_COURSE + CAOService.BREAKING_CHARACTER + courseId + '\n';
                                socketWriter.println(request);
                                response = socketReader.nextLine();
                                socketReader.nextLine();
                                if (response.equals(CAOService.DISPLAY_FAILED)) {
                                    System.out.println("Failed to get course details");
                                } else {
                                    response_split = response.split(CAOService.BREAKING_CHARACTER);
                                    System.out.println(Arrays.toString(response_split));
                                }
                                break;
                            case 3:
                                request = CAOService.DISPLAY_ALL + '\n';
                                socketWriter.println(request);
                                response = socketReader.nextLine();
                                response = response.replace("\"", "");
                                response_split = response.split(CAOService.BREAKING_CHARACTER);
                                System.out.println(response_split[response_split.length - 1]);
                                if (response_split[0].equals(CAOService.SUCCESSFUL_DISPLAY)) {
                                    int i = 1;
                                    while(i < response_split.length) {
                                        System.out.println("-------------------------------");
                                        System.out.println("course id" + response_split[i++]);
                                        System.out.println("level: " + response_split[i++]);
                                        System.out.println("title: " + response_split[i++]);
                                        System.out.println("institution: " + response_split[i++]);
                                        i++;
                                        System.out.println("-------------------------------");
                                    }
                                } else {
                                    System.out.println("Failed to display all courses");
                                }
                                break;
                            case 4:
                                System.out.println("cao number: ");
                                command = in.nextInt();
                                request = CAOService.DISPLAY_CURRENT + CAOService.BREAKING_CHARACTER + command + '\n';
                                socketWriter.println(request);
                                response = socketReader.nextLine();
                                response = response.replace("\"", "");
                                response_split = response.split(CAOService.BREAKING_CHARACTER);
                                if (response_split[0].equals(CAOService.SUCCESSFUL_DISPLAY_CURRENT)) {
                                    int i = 1;
                                    while(i < response_split.length) {
                                        System.out.println("-------------------------------");
                                        System.out.println("course id: " + response_split[i]);
                                        i++;
                                        System.out.println("-------------------------------");
                                    }
                                } else {
                                    System.out.println("Failed to display current courses");
                                }
                                break;
                            case 5:
                                System.out.println("cao number");
                                command = in.nextInt();
                                request = CAOService.UPDATE_CURRENT + CAOService.BREAKING_CHARACTER + command;
                                String updated;
                                for (int i = 0; i < 3; i++) {
                                    System.out.println("course id: ");
                                    updated = in.next();
                                    request += (CAOService.BREAKING_CHARACTER + updated);
                                }
                                request += '\n';
                                socketWriter.println(request);
                                response = socketReader.nextLine();
                                response = response.replace("\"", "");
                                System.out.println(response);
                                if (response.equals(CAOService.SUCCESSFUL_UPDATE_CURRENT)) {
                                    System.out.println("Successfully updated");
                                } else {
                                    System.out.println("Failed to update");
                                }
                                break;
                            default:
                                throw new IllegalArgumentException("Wrong input");
                        }
                    }
                }
            }

            socketWriter.close();
            socketReader.close();
            socket.close();

        } catch (IOException e) {
            System.out.println("Client message: IOException: "+e);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}