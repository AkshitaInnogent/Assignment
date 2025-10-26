package com.innogent.Library.management.System.services;

import com.innogent.Library.management.System.dtos.RequestBookDto;
import com.innogent.Library.management.System.dtos.ResponseBookDto;
import com.innogent.Library.management.System.entities.Book;
import com.innogent.Library.management.System.mappers.BookMapper;
import com.innogent.Library.management.System.repositories.BookRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    public final BookMapper bookMapper;

    public List<ResponseBookDto> getBooks(){
        List<Book> bookList = bookRepository.findAll();
        List<ResponseBookDto> responseBookDtos = new ArrayList<>();
        for(Book book : bookList){
            responseBookDtos.add(bookMapper.convertBooktoBookDto(book));
        }
        return responseBookDtos;
    }

    public ResponseBookDto createBook(RequestBookDto requestBookDto){
        Book newbook = new Book();
        newbook.setBook_name(requestBookDto.getBook_name());
        newbook.setBook_stock(requestBookDto.getBook_stock());
        bookRepository.save(newbook);
        return bookMapper.convertBooktoBookDto(newbook);
    }

    public ResponseBookDto getSingleBook(Long id){
        Book book = bookRepository.getById(id);
        return bookMapper.convertBooktoBookDto(book);
    }

    public ResponseBookDto updateBook(RequestBookDto requestBookDto, Long id){
        Book book = bookRepository.getById(id);
        if(requestBookDto.getBook_name() != null)
            book.setBook_name(requestBookDto.getBook_name());
        if(requestBookDto.getBook_stock() != null)
            book.setBook_stock(requestBookDto.getBook_stock());
        bookRepository.save(book);
        return bookMapper.convertBooktoBookDto(book);
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
