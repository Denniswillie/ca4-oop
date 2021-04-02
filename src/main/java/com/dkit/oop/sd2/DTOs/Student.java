package com.dkit.oop.sd2.DTOs;

import java.util.Objects;

/**
 *D00225319
 *Ethan Alexandro Yiik Hee Sia
 */

public class Student {
    private int caoNumber;  // In the CAO system, cao number is unique identifier for student
    private String dateOfBirth; // yyyy-mm-dd
    private String password;    // min 8 characters


    // Copy Constructor
    // Copies the contents of a Student object argument into
    // a new Student object, and returns that new object (a clone)
    // (add here)
    public Student(Student otherStudent) {
        this.caoNumber = otherStudent.caoNumber;
        this.dateOfBirth = otherStudent.dateOfBirth;
        this.password = otherStudent.password;

    }

    // Constructor
    public Student(int caoNumber, String dateOfBirth, String password ){
        this.caoNumber = caoNumber;
        this.dateOfBirth = dateOfBirth;
        this.password = password;

    }

    //public boolean verifyLoginCredentials( yyy-mm-dd, password);

    public int getCaoNumber() {
        return caoNumber;
    }

    public void setCaoNumber(int caoNumber) {
        this.caoNumber = caoNumber;
    }

    public String getDayOfBirth() {
        return dateOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dateOfBirth = dayOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    @Override
    public String toString() {
        return "Student{" +
                "caoNumber=" + caoNumber +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return caoNumber == student.caoNumber &&
                dateOfBirth.equals(student.dateOfBirth) &&
                password.equals(student.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(caoNumber, dateOfBirth, password);
    }
}

