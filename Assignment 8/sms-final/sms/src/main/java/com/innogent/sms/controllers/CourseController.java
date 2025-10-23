package com.innogent.sms.controllers;

import com.innogent.sms.dtos.RequestCourseDTO;
import com.innogent.sms.dtos.ResponseCourseDTO;
import com.innogent.sms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    @PostMapping(path = "/")
    public ResponseCourseDTO createCourse(@RequestBody RequestCourseDTO requestCourseDTO) {
        return courseService.createCourse(requestCourseDTO);
    }

    @GetMapping(path = "/{id}")
    public ResponseCourseDTO getCourse(@PathVariable int id) {
        return courseService.getCourse(id);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCourse(@PathVariable int id) {
        courseService.delCourse(id);
    }

    @PatchMapping(path = "/{id}")
    public ResponseCourseDTO updateCourse(@PathVariable int id, @RequestBody RequestCourseDTO requestCourseDTO) {
        return courseService.updateCourse(requestCourseDTO,id);
    }

    @GetMapping("/")
    public List<ResponseCourseDTO> getCourseList() {
        return courseService.getAllCourse();
    }

    @GetMapping("/student-count")
    public ResponseEntity<List<Map<String, Object>>> getStudentCountPerCourse() {
        return ResponseEntity.ok(courseService.getStudentCountPerCourse());
    }
    @GetMapping("/without-students")
    public ResponseEntity<List<ResponseCourseDTO>> getCoursesWithoutStudents() {
        return ResponseEntity.ok(courseService.getCoursesWithoutStudents());
    }

    @GetMapping("/top-courses")
    public List<Map<String, Object>> getTopCourses(@RequestParam int limit) {
        return  courseService.getTopNCourses(limit);
    }



}
