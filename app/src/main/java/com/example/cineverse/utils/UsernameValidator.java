package com.example.cineverse.utils;

/**
 * The {@link UsernameValidator} class provides methods for validating usernames based on a specific regular expression.
 * The validation criteria include length constraints, allowed characters, and specific patterns to avoid.
 *
 * <p>The regular expression used for validation is as follows:
 * <pre>
 * ^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?< ![_.])$
 *  └─────┬────┘└───┬──┘└─────┬─────┘└─────┬─────┘ └───┬───┘
 *        │         │         │            │           no _ or . at the end
 *        │         │         │            │
 *        │         │         │            allowed characters
 *        │         │         │
 *        │         │         no __ or _. or ._ or .. inside
 *        │         │
 *        │         no _ or . at the beginning
 *        │
 *        username is 3-20 characters long
 * </pre>
 *
 * <p>This class follows the Singleton pattern, and an instance can be obtained using the {@link #getInstance()} method.
 * Use the {@link #isValid} method to check if a given username is valid according to the defined criteria.
 */
public class UsernameValidator {

    /**
     * Regular expression for username validation
     */
    private static final String USER_REGEX = "^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    /**
     * Private constructor to enforce the singleton pattern.
     */
    private UsernameValidator() {}

    /**
     * Gets the singleton instance of the {@link UsernameValidator} class.
     *
     * @return The {@link UsernameValidator} instance.
     */
    public static UsernameValidator getInstance() {
        return new UsernameValidator();
    }

    /**
     * Validates the given username using the defined regular expression.
     *
     * @param username The username to be validated.
     * @return {@code true} if the username is valid, {@code false} otherwise.
     */
    public boolean isValid(String username) {
        return username.matches(USER_REGEX);
    }

}