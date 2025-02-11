package com.example.author.dto;

import com.example.author.model.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {
    public Author toAuthor(AuthorRequest request) {
        if (request == null){
            return null;
        }
        return Author.builder()
                .id(request.getId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .age(request.getAge())
                .createdAt(request.getCreatedAt())
                .updatedAt(request.getUpdatedAt())
                .build();
    }
    public AuthorResponse fromAuthors(Author author) {
        if (author == null){
            return null;
        }
        return new AuthorResponse(
                author.getId(),
                author.getFirstName(),
                author.getLastName(),
                author.getEmail(),
                author.getAge(),
                author.getCreatedAt(),
                author.getUpdatedAt()
        );
    }
}
