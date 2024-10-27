package com.microservice.student.service;

import com.microservice.student.entities.Student;

import java.util.List;

public interface IStudenService {
    List<Student> findAll();

    Student findById(Long id);

    void save(Student student);

    List<Student> findByIdCourse(Long idCourse);
}
