package com.innogent.Library.management.System.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBookDto {
    private Long book_id;
    private String book_name;
    private Long book_stock;
}