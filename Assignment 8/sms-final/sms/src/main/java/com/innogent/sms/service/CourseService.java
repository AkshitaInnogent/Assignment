package com.innogent.sms.service;

import com.innogent.sms.dtos.RequestCourseDTO;
import com.innogent.sms.dtos.ResponseCourseDTO;
import com.innogent.sms.dtos.ResponseStudentDTO;
import com.innogent.sms.entities.Course;
import com.innogent.sms.entities.Student;
import com.innogent.sms.repositories.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.innogent.sms.mappers.CourseMapper;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public ResponseCourseDTO createCourse(RequestCourseDTO requestCourseDTO) {
        Course newcourse = new Course();
        newcourse.setC_name(requestCourseDTO.getC_name());
        newcourse.setCourse_instructor(requestCourseDTO.getCourse_instructor());
        courseRepository.save(newcourse);
        return courseMapper.convertEntitytoResponseDTO(newcourse);
    }

    public ResponseCourseDTO getCourse(int id) {
        Course getCourse = courseRepository.getById(id);
        return courseMapper.convertEntitytoResponseDTO(getCourse);
    }

    public void delCourse(int id) {
        Course delCourse = courseRepository.getById(id);
        courseRepository.delete(delCourse);
    }

    public ResponseCourseDTO updateCourse(RequestCourseDTO requestCourseDTO, int id) {
        Course updateCourse = courseRepository.getById(id);

        if (requestCourseDTO.getC_name() != null)
            updateCourse.setC_name(requestCourseDTO.getC_name());

        if (requestCourseDTO.getCourse_instructor() != null)
            updateCourse.setCourse_instructor(requestCourseDTO.getCourse_instructor());

        courseRepository.save(updateCourse);
        return courseMapper.convertEntitytoResponseDTO(updateCourse);
    }

    public List<ResponseCourseDTO> getAllCourse() {
        List<ResponseCourseDTO> responseCourseDTOS = new ArrayList<ResponseCourseDTO>();
        List<Course> courseList = courseRepository.findAll();
        for (Course course : courseList) {
            responseCourseDTOS.add(courseMapper.convertEntitytoResponseDTO(course));
        }
        return responseCourseDTOS;
    }

    public List<Map<String, Object>> getStudentCountPerCourse() {
        List<Object[]> result = courseRepository.getStudentCountPerCourse();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : result) {
            Map<String, Object> map = new HashMap<>();
            map.put("courseName", row[0]);
            map.put("studentCount", row[1]);
            response.add(map);
        }
        return response;
    }
    public List<ResponseCourseDTO> getCoursesWithoutStudents() {
        List<Course> courses = courseRepository.getCoursesWithoutStudents();
        return courses.stream()
                .map(courseMapper::convertEntitytoResponseDTO)
                .toList();
    }

    public List<Map<String, Object>> getTopNCourses(int n) {
        List<Object[]> results = courseRepository.getTopNCoursesNative(n);
        List<Map<String, Object>> response = new ArrayList<>();

        for (Object[] row : results) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", row[0]);
            map.put("courseName", row[1]);
            map.put("courseInstructor", row[2]);
            map.put("studentCount", row[3]);
            response.add(map);
        }
        return response;
    }


}