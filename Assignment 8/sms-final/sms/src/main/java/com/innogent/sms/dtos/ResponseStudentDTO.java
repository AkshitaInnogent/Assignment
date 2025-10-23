package com.innogent.sms.dtos;

import com.innogent.sms.entities.Course;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseStudentDTO {
    private int id;
    private String name;
    private Long phone;
    private String email;
    private String city;
    private List<ResponseCourseDTO> courses  = new ArrayList<>();;
}
