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


    @Override
    public String toString() {
        return "CourseChoice{" +
                "student=" + student +
                ", courses=" + courses +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseChoice that = (CourseChoice) o;
        return student == that.student &&
                courses.equals(that.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, courses);
    }
}
