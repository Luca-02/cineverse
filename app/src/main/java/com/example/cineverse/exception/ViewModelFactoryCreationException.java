package com.example.cineverse.exception;

/**
 * The {@link ViewModelFactoryCreationException} class is a custom exception that indicates an
 * error occurred while creating a ViewModel factory.
 */
public class ViewModelFactoryCreationException extends RuntimeException {

    /**
     * Constructs a {@link ViewModelFactoryCreationException} with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public ViewModelFactoryCreationException(Throwable cause) {
        super("Error creating ViewModel factory", cause);
    }

}