package com.example.cineverse.repository.interfaces.auth.service;

import com.example.cineverse.repository.interfaces.IUser;

/**
 * The {@link ILogin} interface extends the {@link IUser} interface and provides a method for user login.
 */
public interface ILogin extends IUser {

    /**
     * Logs in the user using the provided email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     */
    void login(String email, String password);
}