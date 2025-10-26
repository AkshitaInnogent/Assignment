package com.innogent.Library.management.System.controllers;

import com.innogent.Library.management.System.dtos.RequestBookDto;
import com.innogent.Library.management.System.dtos.ResponseBookDto;
import com.innogent.Library.management.System.entities.Book;
import com.innogent.Library.management.System.mappers.BookMapper;
import com.innogent.Library.management.System.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;
    @GetMapping
    public List<ResponseBookDto> getAllBooks() {
        return bookService.getBooks();
    }

    @PostMapping
    public ResponseBookDto createBook(@RequestBody RequestBookDto requestBookDto){
        return bookService.createBook(requestBookDto);
    }

    @GetMapping("/{id}")
    public ResponseBookDto getBook(@PathVariable Long id){
        return bookService.getSingleBook(id);
    }

    @PatchMapping("/{id}")
    public ResponseBookDto updateBook(@RequestBody RequestBookDto requestBookDto , @PathVariable Long id){
        return bookService.updateBook(requestBookDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }

}
