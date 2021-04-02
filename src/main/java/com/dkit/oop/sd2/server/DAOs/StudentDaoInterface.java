package com.dkit.oop.sd2.server.DAOs;

import com.dkit.oop.sd2.server.DTOs.Student;
import com.dkit.oop.sd2.server.Exceptions.DaoException;

import java.util.List;

public interface StudentDaoInterface
{
    public List<Student> findAllStudents() throws DaoException;
    public boolean registerStudent(Student s)throws DaoException;
    public boolean login(Student s)throws DaoException;
    public Student getSpecificStudent(int caoNumber) throws DaoException;
}
