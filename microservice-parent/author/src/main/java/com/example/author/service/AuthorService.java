package com.example.author.service;

import com.example.author.dto.AuthorMapper;
import com.example.author.dto.AuthorRequest;
import com.example.author.dto.AuthorResponse;
import com.example.author.model.Author;
import com.example.author.repository.AuthorRepository;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableMongoRepositories("com.example.author.repository")
public class AuthorService {

    private final AuthorRepository repository;
    private final AuthorMapper mapper;

//    creating an Author POST
    public void createAuthor(AuthorRequest authorRequest){
        Author author = Author.builder()
                .firstName(authorRequest.getFirstName())
                .lastName(authorRequest.getLastName())
                .email(authorRequest.getEmail())
                .age(authorRequest.getAge())
                .build();
        repository.save(author);
        log.info("a new author {} has been added",author.getId());
    }

//    getting all Authors GET
    public List<AuthorResponse> getAllAuthors(){
        List<Author> authors = repository.findAll();
        return authors.stream()
                .map(this::mapToAuthorResponse)
                .collect(Collectors.toList());
    }

    private AuthorResponse mapToAuthorResponse(Author author){
        return AuthorResponse.builder()
                .id(author.getId())
                .firstName(author.getFirstName())
                .lastName(author.getLastName())
                .email(author.getEmail())
                .age(author.getAge())
                .createdAt(author.getCreatedAt())
                .updatedAt(author.getUpdatedAt())
                .build();
    }

//    updating Authors PUT
    public void updateAuthor(AuthorRequest request){
        var author = this.repository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("Cannot update Authors:: No Author found with the provided ID: %s", request.getId())
                ));
        mergeAuthor(author, request);
        repository.save(author);
    }
    public void mergeAuthor(Author author,AuthorRequest request){
        if (StringUtils.isNotBlank(request.getFirstName())){
            author.setFirstName(request.getFirstName());
        }
        if (StringUtils.isNotBlank(request.getLastName())){
            author.setLastName(request.getLastName());
        }
        if (StringUtils.isNotBlank(request.getEmail())){
            author.setEmail(request.getEmail());
        }
        if (request.getAge() != null){
            author.setAge(request.getAge());
        }
        if (request.getCreatedAt() != null){
            author.setCreatedAt(request.getCreatedAt());
        }
        if (request.getUpdatedAt() != null){
            author.setUpdatedAt(request.getUpdatedAt());
        }

    }
//    Deleting by id DELETE
    public void deleteAuthor(String id){
        repository.deleteById(id);
    }
//    findById GET
    public AuthorResponse findById(String id){
        return repository.findById(id)
                .map(mapper::fromAuthors).orElseThrow(() -> new
                RuntimeException(String.format("Cannot find Author with ID: %s", id)));
    }
    public boolean existsById(String id){
        return repository.existsById(id);
    }
    public Optional<Author> getAuthorById(String id){
        List<Author> authors = repository.findAll();
        return authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst();
    }
//    /Delete by id
    public boolean deleteById(String id){
        List<Author> authors = repository.findAll();
        Optional<Author> authorToDeleteById = authors.stream()
                .filter(author -> author.getId().equals(id))
                .findFirst();
        if (authorToDeleteById.isPresent()){
            repository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }


}


