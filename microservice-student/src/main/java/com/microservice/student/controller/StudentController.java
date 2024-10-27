package com.microservice.student.controller;

import com.microservice.student.entities.Student;
import com.microservice.student.service.IStudenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping ("/api/students")
public class StudentController {

    @Autowired
    private IStudenService studentService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveStudent(@RequestBody Student student) {
        studentService.save(student);
    }

    @GetMapping("/all")
    public ResponseEntity<?> findAllStudent() {
        return ResponseEntity.ok(studentService.findAll());
    }
    @GetMapping("/search/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Student student = studentService.findById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/search-ny-course/{idCourse}")
    public ResponseEntity<?> findByIdCourse(@PathVariable Long idCourse){
        return ResponseEntity.ok(studentService.findByIdCourse(idCourse));
    }


}