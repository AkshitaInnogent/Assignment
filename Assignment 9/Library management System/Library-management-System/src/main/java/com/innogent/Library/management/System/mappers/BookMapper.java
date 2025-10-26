package com.innogent.Library.management.System.mappers;

import com.innogent.Library.management.System.dtos.ResponseBookDto;
import com.innogent.Library.management.System.entities.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public ResponseBookDto convertBooktoBookDto(Book book){
        ResponseBookDto responseBookDto = new  ResponseBookDto();
        responseBookDto.setBook_id(book.getBook_id());
        responseBookDto.setBook_name(book.getBook_name());
        responseBookDto.setBook_stock(book.getBook_stock());
        return responseBookDto;
    }
}
