package com.dkit.oop.sd2.BusinessObjects;

/** OOP 2021
 * This App demonstrates the use of a Data Access Object (DAO)
 * to separate Business logic from Database specific logic.
 * It uses DAOs, Data Transfer Objects (DTOs), and
 * a DaoInterface to define a contract between Business Objects
 * and DAOs.
 *
 * "Use a Data Access Object (DAO) to abstract and encapsulate all
 * access to the data source. The DAO manages the connection with
 * the data source to obtain and store data" Ref: oracle.com
 *
 * Here we use one DAO per database table.
 *
 * Use the SQL script included with this project to create the
 * required MySQL user_database and user table
 */



import com.dkit.oop.sd2.DAOs.CourseChoiceDaoInterface;
import com.dkit.oop.sd2.DAOs.MySqlCourseChoiceDao;
import com.dkit.oop.sd2.DAOs.MySqlStudentDao;
import com.dkit.oop.sd2.DAOs.StudentDaoInterface;
import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.DTOs.CourseChoice;
import com.dkit.oop.sd2.DTOs.Student;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.*;


public class App
{

    public static void main(String[] args)
    {
        StudentManager studentManager = new StudentManager();
        Student s = studentManager.getStudent(123456);

        int caoNum =123457;
        String dob ="2000-11-29";
        String password = "123457Ab@";
        studentManager.addStudent(new Student(caoNum,dob,password));

        s= studentManager.getStudent(123457);
        System.out.println("Student: "+s);


        CourseManager cm = new CourseManager();
        Course c =  cm.getCourse("DK135");

        String courseid ="DK136";
        int level = 8;
        String title = "Software";
        String institution ="Cork University";
        cm.addCourse(new Course(courseid,level,title,institution));

        c = cm.getCourse("DK136");
        System.out.println("Course " + c);

        CourseChoiceManager ccm = new CourseChoiceManager();
        List<String>course = new ArrayList<>();
        course.add("DK125");
        course.add("DK128");
        List<String>course1 = new ArrayList<>();
        course1.add("DK158");

        ccm.updateChoices(caoNum,course);
        ccm.updateChoices(123789,course1);


        System.out.println("Course Choices "+ccm.getStudentChoices(caoNum));

    }
}

