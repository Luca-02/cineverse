package com.example.cineverse.interfaces.logged.verify_email;

import com.example.cineverse.interfaces.ILogged;

/**
 * The IVerifyEmail interface extends the ILogged interface and provides methods for email verification.
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