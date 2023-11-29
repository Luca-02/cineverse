package com.example.cineverse.repository.interfaces;

import com.example.cineverse.data.model.user.User;

/**
 * The {@link IUser} interface serves as the base interface for authentication-related functionality.
 */
public interface IUser {
    /**
     * Retrieves the current user associated with the authentication system.
     *
     * @return The {@link User} object representing the current user.
     */
    User getCurrentUser();

    /**
     * Checks whether the email of the current user is verified.
     *
     * @return {@code true} if the user's email is verified, {@code false} otherwise.
     */
    boolean isEmailVerified();
}
