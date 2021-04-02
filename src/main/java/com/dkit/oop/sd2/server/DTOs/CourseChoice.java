package com.dkit.oop.sd2.server.DTOs;

import java.util.*;

public class CourseChoice {
    private int student;
    private ArrayList<String>courses;

    public CourseChoice(int student, ArrayList<String> courses) {
        this.student = student;
        this.courses = courses;
    }

    public int getStudent() {
        return student;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public void addCourse(String courseId) {
        courses.add(courseId);
    }


    //    public void updateChoicesInput()
//    {
//        String ans = "y";
//        String courseId = "";
//        boolean flag = false;
//        String choices = new ArrayList<>();
//        System.out.print("Please input your caoNumber: ");
//        int caoNumber = kb.nextInt();
//        while(ans.equals("y"))
//        {
//
//            System.out.print("Input the courseId with DK + 3 digits : ");
//            courseId = kb.next();
//
//            choices.add(courseId);
//            System.out.println("Do you want to add another course? Please enter 'y' if you want to add another again.");
//            ans=kb.next();
//
//
//        }
//
//
//        selectedChoices.put(caoNumber,choices);
//    }


}
