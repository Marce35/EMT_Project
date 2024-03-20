package com.example.eshop.labs_project.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long id) {
        super(String.format("Book with id: %d is not found", id));
    }

    public BookNotFoundException(String message) {
        super(String.format("Book with name: %s is not found", message));
    }
}