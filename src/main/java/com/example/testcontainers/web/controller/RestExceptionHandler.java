package com.example.testcontainers.web.controller;

import com.example.testcontainers.model.exception.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PostNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String postNotFound(PostNotFoundException e) {
        return "Post not found.";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String postNotValid(MethodArgumentNotValidException e) {
        StringBuilder builder = new StringBuilder();
        for (ObjectError error : e.getAllErrors()) {
            builder.append(error.getDefaultMessage())
                    .append(" ");
        }
        return builder.toString();
    }

}
