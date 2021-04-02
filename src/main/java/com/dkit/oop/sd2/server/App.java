package com.dkit.oop.sd2.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import com.dkit.oop.sd2.core.CAOService;
import com.dkit.oop.sd2.server.DAOs.MySqlCourseChoiceDao;
import com.dkit.oop.sd2.server.DAOs.MySqlStudentDao;
import com.dkit.oop.sd2.server.DAOs.MySqlCourseDao;
import com.dkit.oop.sd2.server.DTOs.Course;
import com.dkit.oop.sd2.server.DTOs.CourseChoice;
import com.dkit.oop.sd2.server.DTOs.Student;
import org.json.simple.JSONValue;

public class App {
    public static void main(String[] args) {
        App server = new App();
        server.start();
    }

    public void start() {
        try {
            ServerSocket ss = new ServerSocket(8080);  // set up ServerSocket to listen for connections on port 8080

            System.out.println("Server: Server started. Listening for connections on port 8080...");

            int clientNumber = 0;  // a number for clients that the server allocates as clients connect

            while (true) {  // loop continuously to accept new client connections{
                Socket socket = ss.accept();    // listen (and wait) for a connection, accept the connection,
                // and open a new socket to communicate with the client
                clientNumber++;

                System.out.println("Server: Client " + clientNumber + " has connected.");

                System.out.println("Server: Port# of remote client: " + socket.getPort());
                System.out.println("Server: Port# of this server: " + socket.getLocalPort());

                Thread t = new Thread(new ClientHandler(socket, clientNumber)); // create a new ClientHandler for the client,
                t.start();                                                  // and run it in its own thread

                System.out.println("Server: ClientHandler started in thread for client " + clientNumber + ". ");
                System.out.println("Server: Listening for further connections...");
            }
        } catch (IOException e) {
            System.out.println("Server: IOException: " + e);
        }
        System.out.println("Server: Server exiting, Goodbye!");
    }

    public class ClientHandler implements Runnable {
        BufferedReader socketReader;
        PrintWriter socketWriter;
        Socket socket;
        int clientNumber;

        public ClientHandler(Socket clientSocket, int clientNumber) {
            try {
                InputStreamReader isReader = new InputStreamReader(clientSocket.getInputStream());
                this.socketReader = new BufferedReader(isReader);

                OutputStream os = clientSocket.getOutputStream();
                this.socketWriter = new PrintWriter(os, true); // true => auto flush socket buffer

                this.clientNumber = clientNumber;  // ID number that we are assigning to this client

                this.socket = clientSocket;  // store socket ref for closing

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;
            MySqlStudentDao studentManager = new MySqlStudentDao();
            MySqlCourseDao courseManager = new MySqlCourseDao();
            MySqlCourseChoiceDao courseChoiceManager = new MySqlCourseChoiceDao();
            try {
                while ((message = socketReader.readLine()) != null) {
                    System.out.println("Server: (ClientHandler): Read command from client " + clientNumber + ": " + message);
                    String[] messageSplit = message.split(CAOService.BREAKING_CHARACTER);
                    int caoNumber;
                    String dateOfBirth, password, courseId;
                    Student student;
                    String response;
                    Course course;
                    List<Course> courses;
                    switch (messageSplit[0]) {
                        case CAOService.REGISTER:
                            caoNumber = Integer.parseInt(messageSplit[1]);
                            dateOfBirth = messageSplit[2];
                            password = messageSplit[3];
                            student = new Student(caoNumber, dateOfBirth, password);
                            if (studentManager.registerStudent(student)) {
                                response = CAOService.REGISTERED;
                            } else {
                                response = "REG FAILED";
                            }
                            socketWriter.println(response);
                            break;
                        case CAOService.LOGIN:
                            caoNumber = Integer.parseInt(messageSplit[1]);
                            dateOfBirth = messageSplit[2];
                            password = messageSplit[3];
                            student = new Student(caoNumber, dateOfBirth, password);
                            if (studentManager.login(student)) {
                                response = CAOService.LOGGED_IN;
                            } else {
                                response = "LOGIN FAILED";
                            }
                            socketWriter.println(response);
                            break;
                        case CAOService.LOGOUT:
                            socketWriter.println(CAOService.LOGGED_OUT);
                            break;
                        case CAOService.DISPLAY_COURSE:
                            courseId = messageSplit[1];
                            course = courseManager.getCourse(courseId);
                            if (course != null) {
                                response = course.getCourseId() + "%%" + course.getLevel() + "%%" + course.getTitle() + "%%" + course.getInstitution() + '\n';
                            } else {
                                response = CAOService.DISPLAY_FAILED;
                            }
                            socketWriter.println(response);
                            break;
                        case CAOService.DISPLAY_ALL:
                            courses = courseManager.findAllCourse();

                            if (courses.size() > 0) {
                                Map obj=new HashMap();
                                obj.put("status",CAOService.SUCCESSFUL_DISPLAY);
                                obj.put("result", courses);
                                response = JSONValue.toJSONString(obj);
                            } else {
                                Map obj=new HashMap();
                                obj.put("status",CAOService.FAILED_DISPLAY_ALL);
                                response = JSONValue.toJSONString(obj);
                            }
                            socketWriter.println(response);
                            break;
                        case CAOService.DISPLAY_CURRENT:
                            caoNumber = Integer.parseInt(messageSplit[1]);
                            CourseChoice courseChoice = courseChoiceManager.getCourseChoiceOfStudent(caoNumber);
                            if (courseChoice == null) {
                                Map obj=new HashMap();
                                obj.put("status",CAOService.FAILED_DISPLAY_CURRENT);
                                response = JSONValue.toJSONString(obj);
                            } else if (courseChoice.getCourses().size() == 0) {
                                Map obj=new HashMap();
                                obj.put("status",CAOService.FAILED_DISPLAY_CURRENT);
                                response = JSONValue.toJSONString(obj);
                            } else {
                                Map obj=new HashMap();
                                obj.put("status",CAOService.SUCCESSFUL_DISPLAY_CURRENT);
                                obj.put("result",courseChoice.getCourses());
                                response = JSONValue.toJSONString(obj);
                            }
                            socketWriter.println(response);
                            break;
                        case CAOService.UPDATE_CURRENT:
                            caoNumber = Integer.parseInt(messageSplit[1]);
                            ArrayList<String> courseIds = new ArrayList<>();
                            for (int i = 2; i < messageSplit.length; i++) {
                                courseIds.add(messageSplit[i]);
                            }
                            if (courseChoiceManager.updateCourseChoice(caoNumber, courseIds)) {
                                response = CAOService.SUCCESSFUL_UPDATE_CURRENT;
                            } else {
                                response = CAOService.FAILED_DISPLAY_CURRENT;
                            }
                            socketWriter.println(response);
                            break;
                        default:
                            socketWriter.println("wrong query");
                    }
                }
                socket.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Server: (ClientHandler): Handler for Client " + clientNumber + " is terminating .....");
        }
    }

}