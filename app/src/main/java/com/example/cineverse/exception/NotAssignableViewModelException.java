package com.example.cineverse.exception;

import androidx.lifecycle.ViewModel;

public class NotAssignableViewModelException extends RuntimeException {

    public NotAssignableViewModelException(
            Class<? extends ViewModel> classToAssign,
            Class<? extends ViewModel> viewModel) {
        super("The class " + viewModel + " is not assignable to " + classToAssign);
    }

}
