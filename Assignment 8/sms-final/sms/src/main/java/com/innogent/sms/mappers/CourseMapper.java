package com.innogent.sms.mappers;

import com.innogent.sms.dtos.ResponseCourseDTO;
import com.innogent.sms.entities.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseMapper {
    public ResponseCourseDTO convertEntitytoResponseDTO(Course course){
        ResponseCourseDTO responseCourseDTO = new ResponseCourseDTO();
        responseCourseDTO.setId(course.getId());
        responseCourseDTO.setC_name(course.getC_name());
        responseCourseDTO.setCourse_instructor(course.getCourse_instructor());
        return responseCourseDTO;
    }
}
