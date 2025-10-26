package com.innogent.Library.management.System.mappers;

import com.innogent.Library.management.System.dtos.ResponseAuthorDto;
import com.innogent.Library.management.System.dtos.ResponseBookDto;
import com.innogent.Library.management.System.entities.Author;
import com.innogent.Library.management.System.entities.Book;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthorMapper {

    public ResponseAuthorDto convertAuthortoAuthorDto(Author author) {
        ResponseAuthorDto dto = new ResponseAuthorDto();
        dto.setAuth_id(author.getAuth_id());
        dto.setAuth_name(author.getAuth_name());
        dto.setAuth_email(author.getAuth_email());

        List<ResponseBookDto> books = new ArrayList<>();
        for (Book book : author.getBooks()) {
            ResponseBookDto bookDto = new ResponseBookDto();
            bookDto.setBook_id(book.getBook_id());
            bookDto.setBook_name(book.getBook_name());
            bookDto.setBook_stock(book.getBook_stock());
            books.add(bookDto);
        }

        dto.setBookList(books);
        return dto;
    }
}
