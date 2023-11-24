package com.example.cineverse.utils.validator;

public class UsernameValidator {

    /*
    ^(?=.{8,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$
     └─────┬────┘└───┬──┘└─────┬─────┘└─────┬─────┘ └───┬───┘
           │         │         │            │           no _ or . at the end
           │         │         │            │
           │         │         │            allowed characters
           │         │         │
           │         │         no __ or _. or ._ or .. inside
           │         │
           │         no _ or . at the beginning
           │
           username is 8-20 characters long
     */
    private static final String USER_REGEX = "^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    public static UsernameValidator getInstance() {
        return new UsernameValidator();
    }

    public boolean isValid(String username) {
        return username.matches(USER_REGEX);
    }

}