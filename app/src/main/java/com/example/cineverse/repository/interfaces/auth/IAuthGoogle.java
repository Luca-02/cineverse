package com.example.cineverse.repository.interfaces.auth;

import android.content.Intent;

import com.example.cineverse.repository.interfaces.IAuth;

/**
 * The IAuthGoogle interface extends the IAuth interface and provides methods for Google authentication.
 */
public interface IAuthGoogle extends IAuth {

    /**
     * Authenticates with Google using the provided data.
     *
     * @param data The Intent data for Google authentication.
     */
    void authWithGoogle(Intent data);
}
