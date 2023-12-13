package com.example.cineverse.exception;

import androidx.lifecycle.ViewModel;

public class UnsupportedViewModelException extends RuntimeException {

    public <T extends ViewModel> UnsupportedViewModelException(Class<T> viewModelClass) {
        super("Unsupported ViewModel class: " + viewModelClass);
    }

}
