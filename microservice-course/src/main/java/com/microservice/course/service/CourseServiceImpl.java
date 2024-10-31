package com.microservice.course.service;

import com.microservice.course.client.StudentClient;
import com.microservice.course.dto.StudentDTO;
import com.microservice.course.entities.Course;
import com.microservice.course.http.response.StudentByCourseResponse;
import com.microservice.course.persistence.ICourseRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
@Service
public class CourseServiceImpl implements ICourseService {

    @Autowired
    private ICourseRepository courseRepository;

    @Autowired
    private StudentClient studentClient;

    @Override
    public List<Course> findAll() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    public Course findById(Long id) {
        return courseRepository.findById(id).orElseThrow();
    }

    @Override
    public void save(Course course) {
        courseRepository.save(course);
    }

    @Override
    @CircuitBreaker(name = "studentService", fallbackMethod = "fallbackFindStudentsByCourse")
    public StudentByCourseResponse findStudentsByCourse(Long idCourse) {
        Course course = courseRepository.findById(idCourse).orElse(new Course());
        List<StudentDTO> studentDTOList = studentClient.findAllStudentByCourse(idCourse);
        return StudentByCourseResponse.builder()
                .courseName(course.getName())
                .teacher(course.getTeacher())
                .studentDTOList(studentDTOList)
                .build();
    }


    public StudentByCourseResponse fallbackFindStudentsByCourse(Long idCourse, Throwable throwable) {
        System.out.println("Circuit Breaker activate: " + throwable.getMessage());
        Course course = courseRepository.findById(idCourse).orElse(new Course());
        return StudentByCourseResponse.builder()
                .courseName(course.getName())
                .teacher(course.getTeacher())
                .studentDTOList(Collections.emptyList()) // Devuelve una lista vacía si falla
                .build();
    }
}

