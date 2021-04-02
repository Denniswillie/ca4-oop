package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.Course;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.io.DataOutput;
import java.util.List;

public interface CourseDaoInterface
{
    public List<Course> findAllCourse() throws DaoException;

    public Course getCourse(String courseId) throws DaoException;

    public void removeCourse(String courseId) throws DaoException;

    public void addCourse(Course course) throws DaoException;
}
