package com.example.cineverse.repository.interfaces.logged.status;

import com.example.cineverse.repository.interfaces.logged.ILogged;

/**
 * The {@link IVerifyEmail} interface extends the {@link ILogged} interface and provides methods for email verification.
 */
public interface IVerifyEmail extends ILogged {

    /**
     * Sends an email verification to the current user.
     */
    void sendEmailVerification();

    /**
     * Reloads user data.
     */
    void reloadUser();
}