package com.innogent.sms.mappers;

import com.innogent.sms.dtos.ResponseStudentDTO;
import com.innogent.sms.entities.Course;
import com.innogent.sms.entities.Student;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor

public class StudentMapper {

    private final CourseMapper courseMapper;
    public ResponseStudentDTO convertEntityToResponseDTO(Student student){
        ResponseStudentDTO responseStudentDTO = new ResponseStudentDTO() ;
        responseStudentDTO.setName(student.getName());
            responseStudentDTO.setPhone(student.getPhone());
            responseStudentDTO.setEmail(student.getEmail());
            responseStudentDTO.setCity(student.getCity());
            responseStudentDTO.setId(student.getId());
            PersistenceUtil persistenceUtil = Persistence.getPersistenceUtil();
            if(persistenceUtil.isLoaded(student, "courses") && student.getCourses() != null){
                for (Course course : student.getCourses()) {
                    responseStudentDTO.getCourses().add(courseMapper.convertEntitytoResponseDTO(course));
                }
            }


        return responseStudentDTO;
        }
//        Persistence.getPersistenceUtil() → gives a tool to check if something is already loaded from the database.
//        PersistenceUtil.isLoaded(student, "courses") → checks if the courses of this student are already loaded in memory.
}
