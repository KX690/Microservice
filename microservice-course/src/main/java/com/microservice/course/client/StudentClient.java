package com.microservice.course.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.microservice.course.dto.StudentDTO;

import java.util.List;

@FeignClient(name = "msvc-student")
public interface StudentClient {
    @GetMapping("/api/students/search-by-course/{idCourse}")
    List<StudentDTO> findAllStudentByCourse(@PathVariable Long idCourse);
}

