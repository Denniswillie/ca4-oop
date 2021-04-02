package com.dkit.oop.sd2.client;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import com.dkit.oop.sd2.core.CAOService;
import com.dkit.oop.sd2.server.DTOs.Course;
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
                    dateOfBirth = in.nextLine();
                    System.out.println("Enter password");
                    password = in.nextLine();
                    request = CAOService.LOGIN + CAOService.BREAKING_CHARACTER + caoNumber + CAOService.BREAKING_CHARACTER + dateOfBirth + CAOService.BREAKING_CHARACTER + password + '\n';
                } else if (command == 2) {
                    System.out.println("Enter cao number: ");
                    caoNumber = in.nextInt();
                    System.out.println("Enter date of birth");
                    dateOfBirth = in.nextLine();
                    System.out.println("Enter password");
                    password = in.nextLine();
                    request = CAOService.REGISTER + CAOService.BREAKING_CHARACTER + caoNumber + CAOService.BREAKING_CHARACTER + dateOfBirth + CAOService.BREAKING_CHARACTER + password + '\n';
                } else {
                    throw new IllegalArgumentException("Wrong input");
                }

                socketWriter.println(request);

                String response;
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
                                if (socketReader.nextLine().equals(CAOService.LOGGED_OUT)) {
                                    run = false;
                                }
                                break;
                            case 2:
                                System.out.println("Enter course id");
                                courseId = in.nextLine();
                                request = CAOService.DISPLAY_COURSE + CAOService.BREAKING_CHARACTER + courseId + '\n';
                                socketWriter.println(request);
                                response = socketReader.nextLine();
                                if (response.equals(CAOService.DISPLAY_FAILED)) {
                                    System.out.println("Failed to get course details");
                                } else {
                                    String[] response_split = response.split(CAOService.BREAKING_CHARACTER);
                                    System.out.println("courseId: " + response_split[0]);
                                    System.out.println("level: " + response_split[1]);
                                    System.out.println("title: " + response_split[2]);
                                    System.out.println("institution: " + response_split[3]);
                                }
                                break;
                            case 3:
                                request = CAOService.DISPLAY_ALL + '\n';
                                socketWriter.println(request);
                                response = socketReader.nextLine();
                                Object obj=JSONValue.parse(response);
                                JSONObject jsonObject = (JSONObject) obj;
                                String status = (String) jsonObject.get("status");
                                if (status.equals(CAOService.SUCCESSFUL_DISPLAY)) {
                                    List<Course> courses = (List) jsonObject.get("result");
                                    for (int i = 0; i < courses.size(); i++) {
                                        System.out.println("-------------------------------");
                                        System.out.println("course id: " + courses.get(i).getCourseId());
                                        System.out.println("level: " + courses.get(i).getLevel());
                                        System.out.println("title: " + courses.get(i).getTitle());
                                        System.out.println("institution: " + courses.get(i).getInstitution());
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
                                Object obj2=JSONValue.parse(response);
                                JSONObject jsonObject2 = (JSONObject) obj2;
                                String status2 = (String) jsonObject2.get("status");
                                if (status2.equals(CAOService.SUCCESSFUL_DISPLAY_CURRENT)) {
                                    List<Course> courses = (List) jsonObject2.get("result");
                                    for (int i = 0; i < courses.size(); i++) {
                                        System.out.println("-------------------------------");
                                        System.out.println("course id: " + courses.get(i).getCourseId());
                                        System.out.println("level: " + courses.get(i).getLevel());
                                        System.out.println("title: " + courses.get(i).getTitle());
                                        System.out.println("institution: " + courses.get(i).getInstitution());
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
                                if (response.equals(CAOService.SUCCESSFUL_DISPLAY_CURRENT)) {
                                    System.out.println("Successfully updated");
                                } else {
                                    System.out.println("Failed to updated");
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