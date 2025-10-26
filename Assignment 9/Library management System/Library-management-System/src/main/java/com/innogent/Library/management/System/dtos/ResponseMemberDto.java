package com.innogent.Library.management.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMemberDto {
    private Long member_id;
    private String member_name;
    private String member_email;
    private Set<ResponseBookDto> borrowed_books = new HashSet<>();
}