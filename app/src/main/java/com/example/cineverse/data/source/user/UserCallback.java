package com.example.cineverse.data.source.user;

import com.example.cineverse.service.NetworkCallback;

/**
 * The {@link UserCallback <T>} interface extends {@link NetworkCallback} and defines a method
 * to handle asynchronous callbacks for user management with one parameter.
 *
 * @param <T> The type of the parameter for the callback.
 */
public interface UserCallback<T> extends NetworkCallback {

    void onCallback(T data);

}