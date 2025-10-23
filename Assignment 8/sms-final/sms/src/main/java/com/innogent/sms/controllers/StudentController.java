package com.innogent.sms.controllers;

import com.innogent.sms.dtos.RequestStudentDTO;
import com.innogent.sms.dtos.ResponseCourseDTO;
import com.innogent.sms.dtos.ResponseStudentDTO;
import com.innogent.sms.service.CourseService;
import com.innogent.sms.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/students")
public class StudentController {
    private final StudentService studentService ;
    private final CourseService courseService;

    @PostMapping(path = "/")
    public ResponseStudentDTO CreateStudent(@RequestBody RequestStudentDTO requestStudentDTO){
        return(studentService.createStudent(requestStudentDTO));
    }

    @GetMapping(path = "/{id}")
    public ResponseStudentDTO GetStudent(@PathVariable int id){
        return(studentService.getStudent(id));
    }
    @DeleteMapping(path = "/{id}")
    public void DeleteStudent(@PathVariable int id){
        studentService.deleteStudent(id);
    }
    @PatchMapping(path = "/{id}")
    public ResponseStudentDTO UpdateStudent(@PathVariable int id, @RequestBody RequestStudentDTO requestStudentDTO){
        return studentService.updateStudent(id,requestStudentDTO);
    }

    @GetMapping("/")
    public List<ResponseStudentDTO> GetAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/course/{courseName}")
    public Set<ResponseStudentDTO> getCourseByCourseName(@PathVariable String courseName) {
        return studentService.getStudentsByCourseName(courseName);
    }

    @GetMapping("/search")
    public List<ResponseStudentDTO> getStudentsByCourseName(@RequestParam String city , @RequestParam String instructorName) {
        return studentService.getStudentsByCityAndInstructor(city,instructorName);
    }

    @GetMapping("/no-course")
    public List<ResponseStudentDTO> getNoCourse(){
        return studentService.getStudentsWithoutCourses();
    }

}
