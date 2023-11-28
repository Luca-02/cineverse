package com.example.cineverse.repository.interfaces.auth.service;

import com.example.cineverse.repository.interfaces.IUser;

/**
 * The {@link IRegister} interface extends the {@link IUser} interface and provides a method for user registration.
 */
public interface IRegister extends IUser {

    /**
     * Registers a new user with the provided email and password.
     *
     * @param email    The user's email address for registration.
     * @param password The user's password for registration.
     */
    void register(String username, String email, String password);
}