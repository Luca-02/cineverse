package com.example.cineverse.interfaces.auth;

import com.example.cineverse.interfaces.IAuth;

/**
 * The IForgotPassword interface extends the IAuth interface and provides a method for handling password recovery.
 */
public interface IForgotPassword extends IAuth {

    /**
     * Sends a password recovery request to the provided email address.
     *
     * @param email The email address for password recovery.
     */
    void forgotPassword(String email);
}