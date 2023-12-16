package com.example.cineverse.exception;

import androidx.lifecycle.ViewModel;

/**
 * The {@link NotAssignableViewModelException} class is a custom exception that indicates
 * a ViewModel is not assignable to a specified class.
 */
public class NotAssignableViewModelException extends RuntimeException {

    /**
     * Constructs a {@link NotAssignableViewModelException} with the specified classes.
     *
     * @param classToAssign The class to assign the {@link ViewModel} to.
     * @param viewModel     The {@link ViewModel} class.
     */
    public NotAssignableViewModelException(
            Class<? extends ViewModel> classToAssign,
            Class<? extends ViewModel> viewModel) {
        super("The class " + viewModel + " is not assignable to " + classToAssign);
    }

}
