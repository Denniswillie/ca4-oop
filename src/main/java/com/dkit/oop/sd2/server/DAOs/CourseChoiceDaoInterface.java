package com.dkit.oop.sd2.server.DAOs;

import com.dkit.oop.sd2.server.DTOs.CourseChoice;
import com.dkit.oop.sd2.server.Exceptions.DaoException;

import java.util.List;

public interface CourseChoiceDaoInterface
{
    public List<CourseChoice> findAllCourseChoice() throws DaoException;
    public List<CourseChoice> insertCourseChoice(int caoNumber,List<String>courseId) throws DaoException;
}
