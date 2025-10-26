package com.innogent.Library.management.System.controllers;

import com.innogent.Library.management.System.dtos.RequestAuthorDto;
import com.innogent.Library.management.System.dtos.ResponseAuthorDto;

import com.innogent.Library.management.System.services.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping
    public ResponseAuthorDto createAuthor(@RequestBody RequestAuthorDto requestAuthorDto) {
        return authorService.createAuthor(requestAuthorDto);
    }

    @GetMapping
    public List<ResponseAuthorDto> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @GetMapping("/{id}")
    public ResponseAuthorDto getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    @PutMapping("/{id}")
    public ResponseAuthorDto updateAuthor(@RequestBody RequestAuthorDto requestAuthorDto, @PathVariable Long id) {
        return authorService.updateAuthor(requestAuthorDto, id);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
