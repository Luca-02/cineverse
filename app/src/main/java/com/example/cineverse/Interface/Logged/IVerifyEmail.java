package com.example.cineverse.Interface.Logged;

import com.example.cineverse.Interface.ILogged;

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