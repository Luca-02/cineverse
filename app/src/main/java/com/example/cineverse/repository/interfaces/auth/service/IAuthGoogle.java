package com.example.cineverse.repository.interfaces.auth.service;

import android.content.Intent;

import com.example.cineverse.repository.interfaces.IUser;

/**
 * The {@link IAuthGoogle} interface extends the {@link IUser} interface and provides methods for Google authentication.
 */
public interface IAuthGoogle extends IUser {

    /**
     * Authenticates with Google using the provided data.
     *
     * @param data The Intent data for Google authentication.
     */
    void authWithGoogle(Intent data);
}
