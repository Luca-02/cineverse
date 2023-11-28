package com.example.cineverse.repository.interfaces.auth;

import com.example.cineverse.repository.interfaces.IAuth;

/**
 * The ILogin interface extends the IAuth interface and provides a method for user login.
 */
public interface ILogin extends IAuth {

    /**
     * Logs in the user using the provided email and password.
     *
     * @param email    The user's email address.
     * @param password The user's password.
     */
    void login(String email, String password);
}