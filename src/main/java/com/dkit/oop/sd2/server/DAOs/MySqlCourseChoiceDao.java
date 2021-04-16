package com.dkit.oop.sd2.server.DAOs;

import com.dkit.oop.sd2.server.DTOs.CourseChoice;
import com.dkit.oop.sd2.server.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseChoiceDao extends MySqlDao implements CourseChoiceDaoInterface
{
    @Override
    public List<CourseChoice> findAllCourseChoice() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CourseChoice> courseChoices = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM STUDENT_COURSES";


            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();


            while (rs.next())
            {
                int caoNumber = rs.getInt("caoNumber");

                String courseId= rs.getString("courseid");
                boolean isExist = false;
                for (int i = 0; i < courseChoices.size(); i++) {
                    if (courseChoices.get(i).getStudent() == caoNumber) {
                        isExist = true;
                        courseChoices.get(i).addCourse(courseId);
                    }
                }

                if (!isExist) {
                    ArrayList<String> temp = new ArrayList<>();
                    temp.add(courseId);
                    courseChoices.add(new CourseChoice(caoNumber, temp));
                }
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllCourseChoices() " + e.getMessage());
        } finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (ps != null)
                {
                    ps.close();
                }
                if (con != null)
                {
                    freeConnection(con);
                }
            } catch (SQLException e)
            {
                throw new DaoException("findAllCourseChoices() " + e.getMessage());
            }
        }
        return courseChoices;     // may be empty
    }


    @Override
    public boolean updateCourseChoice(int caoNumber, List<String>courseId) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CourseChoice> courseChoice = new ArrayList<>();
        boolean success = true;
        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query;
            PreparedStatement preparedStmt;

//            String query = "Insert into student_courses(caoNumber, courseId)" + " values (?, ?)";
            query = "DELETE from student_courses WHERE caoNumber = ?;";

            preparedStmt = con.prepareStatement(query);
            preparedStmt.setInt(1,caoNumber);
            preparedStmt.execute();

            query = "Insert into student_courses(caoNumber, courseId) values (?, ?)";
            for (String id: courseId) {
                preparedStmt = con.prepareStatement(query);
                preparedStmt.setInt(1,caoNumber);
                preparedStmt.setString(2, id);
                preparedStmt.execute();
            }
            con.close();

        } catch (SQLException e) {
            success = false;
            System.out.println(e);
            throw new DaoException("findAllCourseChoices() " + e.getMessage());
        } finally {
            return success;
        }
    }

    @Override
    public CourseChoice getCourseChoiceOfStudent(int caoNumber) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CourseChoice courseChoice = null;
        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM student_courses WHERE caoNumber = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, Integer.toString(caoNumber));

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            ArrayList<String> courseIds = new ArrayList<>();
            while (rs.next()) {
                String courseId = rs.getString("courseid");
                courseIds.add(courseId);
            }
            courseChoice = new CourseChoice(caoNumber, courseIds);
        } catch (SQLException e) {
            throw new DaoException("findAllCourse() " + e.getMessage());
        } finally {
            return courseChoice;
        }
    }


}



