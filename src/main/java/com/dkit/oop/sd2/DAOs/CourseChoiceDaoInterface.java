package com.dkit.oop.sd2.DAOs;

import com.dkit.oop.sd2.DTOs.CourseChoice;
import com.dkit.oop.sd2.Exceptions.DaoException;

import java.util.List;

public interface CourseChoiceDaoInterface
{
    public List<CourseChoice> findAllCourseChoice() throws DaoException;
    public List<CourseChoice> insertCourseChoice(int caoNumber,List<String>courseId) throws DaoException;
}
