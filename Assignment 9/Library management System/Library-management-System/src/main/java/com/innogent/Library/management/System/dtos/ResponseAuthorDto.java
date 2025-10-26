package com.innogent.Library.management.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAuthorDto {
    private  Long auth_id;
    private  String auth_name;
    private  String auth_email;
    private List<ResponseBookDto> bookList = new ArrayList<>();
}
