package com.dkit.oop.sd2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.dkit.oop.sd2.DTOs.Student;
import org.junit.Test;

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
    public void testAddStudentToStudentManager()
    {
        StudentManager studentManager = new StudentManager();

        int caoNum =123457;
        String dob ="2000-11-29";
        String password = "123457Ab@";
        Student expected = new Student(caoNum,dob,password);

        studentManager.addStudent(expected);

        Student actual= studentManager.getStudent(123457);

        assertTrue(actual.equals(expected));

    }
    
//    @Test
//    tsetStudentDao(){
//        //StudentDaoInterface studentDao = new MySqlStudentDao();
//        //studentDao.
//    }
}
