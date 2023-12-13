package com.example.cineverse.exception;

public class ViewModelCreationException extends RuntimeException {

    public ViewModelCreationException(Throwable cause) {
        super("Error creating ViewModel", cause);
    }

}