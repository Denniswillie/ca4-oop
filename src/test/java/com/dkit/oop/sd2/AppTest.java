package com.dkit.oop.sd2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.dkit.oop.sd2.server.DAOs.*;
import com.dkit.oop.sd2.server.DTOs.Course;
import com.dkit.oop.sd2.server.DTOs.CourseChoice;
import com.dkit.oop.sd2.server.DTOs.Student;
import com.dkit.oop.sd2.server.Exceptions.DaoException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    
    @Test
    public void testRegisterDao() throws DaoException {
        StudentDaoInterface studentDao = new MySqlStudentDao();
        int caoNum =224444;
        String dob ="1998-10-05";
        String password = "12545785Ab@";
        Student expected = new Student(caoNum,dob,password);
        studentDao.registerStudent(expected);
        Student actual = studentDao.getSpecificStudent(caoNum);
        System.out.println(actual);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testLoginDao() throws DaoException
    {

        StudentDaoInterface studentDao = new MySqlStudentDao();
        int caoNum =123456;
        String dob ="1990-30-12";
        String password = "user123456";
        Student s = new Student(caoNum,dob,password);
        boolean expected = studentDao.login(s);
        boolean actual = true;
        assertEquals(expected,actual);


    }



    @Test
    public void testfindAllCourseDao() throws DaoException
    {
        HashMap<String,Course> courseMap= new HashMap<>();

        CourseDaoInterface courseDao = new MySqlCourseDao();
        List<Course>courseList = courseDao.findAllCourse();
        for(Course course : courseList)
        {
            courseMap.put(course.getCourseId(),course);

        }
        System.out.println("Course Map" + courseMap);


    }

    @Test
    public void testGetCourseDao() throws DaoException
    {
        CourseDaoInterface courseDao = new MySqlCourseDao();
        String courseId = "DK136";
        int level = 8;
        String title ="Maths";
        String institution = "MIT";
        Course expected = new Course(courseId,level,title,institution);
        Course actual = courseDao.getCourse(courseId);
        System.out.println(expected);
        System.out.println(actual);
        assertEquals(expected,actual);


    }

    @Test
    public void testGetCourseChoiceOfStudentDao() throws DaoException
    {
        CourseChoiceDaoInterface courseChoiceDao = new MySqlCourseChoiceDao();
        int caoNumber = 123456;
        ArrayList<String>courseid = new ArrayList<>();
        courseid.add("DK135");
        courseid.add("DK136");
        courseid.add("DK137");
        courseChoiceDao.updateCourseChoice(caoNumber, new ArrayList<>(Arrays.asList("DK135", "DK136", "DK137")));
        CourseChoice expected = new CourseChoice(caoNumber,courseid);
        CourseChoice actual = courseChoiceDao.getCourseChoiceOfStudent(caoNumber);
        System.out.println(actual);
        assertEquals(expected,actual);


    }

    @Test
    public void testUpdateCourseChoiceDao() throws DaoException
    {
        CourseChoiceDaoInterface courseChoiceDao = new MySqlCourseChoiceDao();
        int caoNumber = 123456;
        ArrayList<String>courseid = new ArrayList<>();
        courseid.add("DK137");
        courseid.add("DK138");
        boolean expected =courseChoiceDao.updateCourseChoice(caoNumber,courseid);
        boolean actual = true;
        assertEquals(expected,actual);


    }







}
