package com.example.cineverse.exception;

public class ViewTypeNotFoundException extends RuntimeException {

    public ViewTypeNotFoundException(int viewType) {
        super("View type [" + viewType + "] not found");
    }

}
