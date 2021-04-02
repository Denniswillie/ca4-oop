package com.dkit.oop.sd2.server.DAOs;

import com.dkit.oop.sd2.server.DTOs.CourseChoice;
import com.dkit.oop.sd2.server.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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
    public List<CourseChoice> updateCourseChoice(int caoNumber,List<String>courseId) throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CourseChoice> courseChoice = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = " Insert into student_courses(caoNumber, courseId)"
                    + " values (?, ?)";



            PreparedStatement preparedStmt = con.prepareStatement(query);

            preparedStmt.setInt(1,caoNumber);
            for(String cId : courseId)
            {
                preparedStmt.setString(2,cId);
            }


            preparedStmt.execute();

            con.close();

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
        return courseChoice;     // may be empty
    }


}



