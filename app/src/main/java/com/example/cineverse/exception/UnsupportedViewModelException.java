package com.example.cineverse.exception;

import androidx.lifecycle.ViewModel;

/**
 * The {@link UnsupportedViewModelException} class is a custom exception that indicates an
 * attempt to use an unsupported ViewModel class.
 */
public class UnsupportedViewModelException extends RuntimeException {

    /**
     * Constructs an {@link UnsupportedViewModelException} with the specified ViewModel class.
     *
     * @param viewModelClass The unsupported {@link ViewModel} class.
     * @param <T>             The type of {@link ViewModel}.
     */
    public <T extends ViewModel> UnsupportedViewModelException(Class<T> viewModelClass) {
        super("Unsupported ViewModel class: " + viewModelClass);
    }

}
