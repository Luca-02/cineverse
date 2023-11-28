package com.example.cineverse.repository.interfaces.auth.service;

import com.example.cineverse.repository.interfaces.IUser;

/**
 * The {@link IForgotPassword} interface extends the {@link IUser} interface and provides a method for handling password recovery.
 */
public interface IForgotPassword extends IUser {

    /**
     * Sends a password recovery request to the provided email address.
     *
     * @param email The email address for password recovery.
     */
    void forgotPassword(String email);
}