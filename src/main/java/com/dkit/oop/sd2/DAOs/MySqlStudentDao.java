package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Student;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlStudentDao extends MySqlDao implements StudentDaoInterface
{
    @Override
    public List<Student> findAllStudents() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Student> student = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM STUDENT";


            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {
                int caoNumber= rs.getInt("caoNumber");
                String date_of_birth = rs.getString("date_of_birth");
                String password = rs.getString("password");
                Student s = new Student(caoNumber, date_of_birth, password);
                student.add(s);
            }
        } catch (SQLException e)
        {
            throw new DaoException("findAllUsers() " + e.getMessage());
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
                throw new DaoException("findAllUsers() " + e.getMessage());
            }
        }
        return student;     // may be empty
    }


    @Override
     public boolean registerStudent(Student s)throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "INSERT INTO STUDENT VALUES(?,?,?)";


            ps = con.prepareStatement(query);

            ps.setInt(1,s.getCaoNumber());
            ps.setString(2,s.getDayOfBirth());
            ps.setString(3,s.getPassword());

            success =(ps.executeUpdate() == 1);
        } catch (SQLException e)
        {
            throw new DaoException("registerStudent() " + e.getMessage());
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
                throw new DaoException("registerStudent() " + e.getMessage());
            }
        }
        return success;
    }

    @Override
    public boolean login(Student s)throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean success = false;

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM STUDENT WHERE caoNumber = ? AND password = ?";


            ps = con.prepareStatement(query);

            ps.setInt(1,s.getCaoNumber());
            ps.setString(2,s.getPassword());

            rs = ps.executeQuery(query);
            if(rs != null)
            {
                success = true;
            }

        } catch (SQLException e)
        {
            throw new DaoException("login() " + e.getMessage());
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
                throw new DaoException("login() " + e.getMessage());
            }
        }
        return success;
    }



}
