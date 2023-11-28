package com.example.cineverse.repository.interfaces.logged;

import com.example.cineverse.repository.interfaces.IUser;

/**
 * The {@link ILogged} interface extends the {@link IUser} interface, serves as the base interface for logged-in user functionality.
 */
public interface ILogged extends IUser {

    /**
     * Logs out the currently logged-in user.
     */
    void logOut();
}
