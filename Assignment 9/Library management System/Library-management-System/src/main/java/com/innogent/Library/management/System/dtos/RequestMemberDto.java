package com.innogent.Library.management.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestMemberDto {
    private String member_name;
    private String member_email;
}