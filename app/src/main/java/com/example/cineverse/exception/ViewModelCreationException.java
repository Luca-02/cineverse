package com.example.cineverse.exception;

/**
 * The {@link ViewModelCreationException} class is a custom exception that indicates an
 * error occurred while creating a ViewModel.
 */
public class ViewModelCreationException extends RuntimeException {

    /**
     * Constructs a {@link ViewModelCreationException} with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public ViewModelCreationException(Throwable cause) {
        super("Error creating ViewModel", cause);
    }

}