package com.example.author.controller;

import com.example.author.dto.AuthorRequest;
import com.example.author.dto.AuthorResponse;
import com.example.author.model.Author;
import com.example.author.repository.AuthorRepository;
import com.example.author.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor

public class AuthorController {

    @Autowired
    private final AuthorService service;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createAuthor(@RequestBody AuthorRequest authorRequest){
        service.createAuthor(authorRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorResponse> getAllAuthors(){
        return service.getAllAuthors();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AuthorResponse getAuthorById(@PathVariable String id){
        return service.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateAuthor(@RequestBody AuthorRequest request){
        service.updateAuthor(request);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteAuthor(@PathVariable("id") String id){
        boolean isDeleted = service.deleteById(id);
        if (isDeleted){
            return "Deleted Author Successfully";
        }else {
            return "Author cannot be found by ID";
        }
    }

}
