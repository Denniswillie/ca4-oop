package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCourseDao extends MySqlDao implements CourseDaoInterface {
    @Override
    public List<Course> findAllCourse() throws DaoException
    {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Course> courses = new ArrayList<>();

        try
        {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();
            String query = "SELECT * FROM COURSE";
            ps = con.prepareStatement(query);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            while (rs.next())
            {
                String courseId = rs.getString("courseid");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");
                Course c = new Course(courseId, level, title, institution);
                courses.add(c);
            }
            return courses;
        } catch (SQLException e) {
            throw new DaoException("findAllCourse() " + e.getMessage());
        }
    }

    @Override
    public Course getCourse(String id) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            //Get connection object using the methods in the super class (MySqlDao.java)...
            con = this.getConnection();

            String query = "SELECT * FROM COURSE WHERE courseId = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, id);

            //Using a PreparedStatement to execute SQL...
            rs = ps.executeQuery();
            if (rs.next()) {
                String courseId = rs.getString("courseId");
                int level = rs.getInt("level");
                String title = rs.getString("title");
                String institution = rs.getString("institution");
                return new Course(courseId, level, title, institution);
            }
            return null;
        } catch (SQLException e) {
            throw new DaoException("findAllCourse() " + e.getMessage());
        }
    }

    @Override
    public void removeCourse(String courseId) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = this.getConnection();
            String query = "DELETE FROM COURSE WHERE courseId = ?;";
            ps = con.prepareStatement(query);
            ps.setString(1, courseId);
            ps.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("removeCourse() " + e.getMessage());
        }
    }

    @Override
    public void addCourse(Course course) throws DaoException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = this.getConnection();
            String query = "INSERT INTO COURSE VALUES(?, ?, ?, ?)";
            ps = con.prepareStatement(query);
            ps.setString(1, course.getCourseId());
            ps.setInt(2, course.getLevel());
            ps.setString(3, course.getTitle());
            ps.setString(4, course.getInstitution());
            ps.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("addCourse() " + e.getMessage());
        }
    }
}
