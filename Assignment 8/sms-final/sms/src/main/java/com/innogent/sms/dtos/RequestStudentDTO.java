package com.innogent.sms.dtos;

import lombok.Data;

import java.util.List;

@Data
public class RequestStudentDTO {
    private String name;
    private Long phone;
    private String email;
    private String city;
    private List<Integer> courseIds;
}
