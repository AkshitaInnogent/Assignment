package com.innogent.Library.management.System.services;

import com.innogent.Library.management.System.dtos.RequestAuthorDto;
import com.innogent.Library.management.System.dtos.ResponseAuthorDto;
import com.innogent.Library.management.System.entities.Author;
import com.innogent.Library.management.System.entities.Book;
import com.innogent.Library.management.System.mappers.AuthorMapper;
import com.innogent.Library.management.System.repositories.AuthorRepository;
import com.innogent.Library.management.System.repositories.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final AuthorMapper authorMapper;

    public ResponseAuthorDto createAuthor(RequestAuthorDto requestAuthorDto) {
        Author author = new Author();
        author.setAuth_name(requestAuthorDto.getAuth_name());
        author.setAuth_email(requestAuthorDto.getAuth_email());

        List<Long> bookIds = requestAuthorDto.getBook_ids();
        if (!bookIds.isEmpty()) {
            List<Book> books = bookRepository.findAllById(bookIds);
            if (books.size() != bookIds.size()) {
                throw new EntityNotFoundException("One or more books not found");
            }
            author.setBooks(books);
            for (Book book : books) {
                book.setAuthor(author);
            }
        }

        Author savedAuthor = authorRepository.save(author);
        return authorMapper.convertAuthortoAuthorDto(savedAuthor);
    }

    public List<ResponseAuthorDto> getAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        List<ResponseAuthorDto> authorDtos = new ArrayList<>();
        for (Author author : authors) {
            ResponseAuthorDto dto = authorMapper.convertAuthortoAuthorDto(author);
            authorDtos.add(dto);
        }
        return authorDtos;
    }

    public ResponseAuthorDto getAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));
        return authorMapper.convertAuthortoAuthorDto(author);
    }

    public ResponseAuthorDto updateAuthor(RequestAuthorDto requestAuthorDto, Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));

        author.setAuth_name(requestAuthorDto.getAuth_name());
        author.setAuth_email(requestAuthorDto.getAuth_email());

        List<Long> bookIds = requestAuthorDto.getBook_ids();
        if (!bookIds.isEmpty()) {
            List<Book> books = bookRepository.findAllById(bookIds);
            if (books.size() != bookIds.size()) {
                throw new EntityNotFoundException("One or more books not found");
            }
            for (Book book : author.getBooks()) {
                book.setAuthor(null);
            }
            author.getBooks().clear();
            author.setBooks(books);
            for (Book book : books) {
                book.setAuthor(author);
            }
        } else {
            for (Book book : author.getBooks()) {
                book.setAuthor(null);
            }
            author.getBooks().clear();
        }

        Author updatedAuthor = authorRepository.save(author);
        return authorMapper.convertAuthortoAuthorDto(updatedAuthor);
    }

    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Author with ID " + id + " not found"));

        for (Book book : author.getBooks()) {
            book.setAuthor(null);
        }
        author.getBooks().clear();
        authorRepository.delete(author);
    }
}