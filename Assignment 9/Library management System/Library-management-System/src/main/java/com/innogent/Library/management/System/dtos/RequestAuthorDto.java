package com.innogent.Library.management.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestAuthorDto {
    private  String auth_name;
    private  String auth_email;
    private  List<Long> book_ids = new ArrayList<>();
}
