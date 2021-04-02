package com.dkit.oop.sd2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.dkit.oop.sd2.server.DAOs.CourseDaoInterface;
import com.dkit.oop.sd2.server.DAOs.MySqlCourseDao;
import com.dkit.oop.sd2.server.DAOs.MySqlStudentDao;
import com.dkit.oop.sd2.server.DAOs.StudentDaoInterface;
import com.dkit.oop.sd2.server.DTOs.Course;
import com.dkit.oop.sd2.server.DTOs.Student;
import com.dkit.oop.sd2.server.Exceptions.DaoException;
import org.junit.Test;

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
//        studentDao.registerStudent(expected);
        Student actual = studentDao.getSpecificStudent(caoNum);
        System.out.println(actual);
        assertTrue(actual.equals(expected));
    }

    @Test
    public void testLoginDao() throws DaoException
    {

        StudentDaoInterface studentDao = new MySqlStudentDao();
        int caoNum =224444;
        String dob ="1999-10-05";
        String password = "125457Ab@";
        Student s = new Student(caoNum,dob,password);
        boolean expected = studentDao.login(s);
        boolean actual = true;
        assertEquals(expected,actual);


    }



//    @Test
//    public void testfindAllCourseDao() throws DaoException
//    {
//
//        CourseDaoInterface courseDao = new MySqlCourseDao();
//        List<Course>courseList = courseDao.findAllCourse();
//        for(Course course : courseList)
//        {
//
//        }
//
//
//
//    }
}
