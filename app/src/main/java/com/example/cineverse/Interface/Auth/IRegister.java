package com.example.cineverse.Interface.Auth;

import com.example.cineverse.Interface.IAuth;

/**
 * The IRegister interface extends the IAuth interface and provides a method for user registration.
 */
public interface IRegister extends IAuth {

    /**
     * Registers a new user with the provided email and password.
     *
     * @param email    The user's email address for registration.
     * @param password The user's password for registration.
     */
    void register(String email, String password);
}