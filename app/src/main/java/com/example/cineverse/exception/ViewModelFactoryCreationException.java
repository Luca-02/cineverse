package com.example.cineverse.exception;

public class ViewModelFactoryCreationException extends RuntimeException {

    public ViewModelFactoryCreationException(Throwable cause) {
        super("Error creating ViewModel factory", cause);
    }

}