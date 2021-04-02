package com.dkit.oop.sd2.server.DTOs;

/**
 *D00225319
 *Ethan Alexandro Yiik Hee Sia
 */

public class Course {

    private String courseId;   // e.g. DK821
    private int level;      // e.g. 7, 8, 9, 10
    private String title;      // e.g. BSc in Computing in Software Development
    private String institution; // Dundalk Institute of Technology

    // Copy Constructor
    // Accepts a Course object as an argument and copies all the field values
    // into a new Course object. Returns the new cloned object.
    // (add here)

    public Course(Course otherStudent) {
        this.courseId = otherStudent.courseId;
        this.level = otherStudent.level;
        this.title = otherStudent.title;
        this.institution = otherStudent.institution;
    }

    // Constructor
    public Course(String courseId, int level, String title, String institution) {
        this.courseId = courseId;
        this.level = level;
        this.title = title;
        this.institution = institution;
    }



    public String getCourseId() {
        return courseId;
    }
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getInstitution() {
        return institution;
    }
    public void setInstitution(String institution) {
        this.institution = institution;
    }
    public String getTitle() { return title;  }
    public void setTitle(String title) { this.title = title; }

    @Override
    public String toString() {
        return "Course{" +
                "courseId='" + courseId + '\'' +
                ", level='" + level + '\'' +
                ", institution='" + institution + '\'' +
                '}';
    }
}
