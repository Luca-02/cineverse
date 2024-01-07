package com.example.cineverse.service.firebase;

import com.example.cineverse.service.NetworkCallback;

/**
 * The {@link FirebaseCallback <T>} interface extends {@link NetworkCallback} and defines a method
 * to handle asynchronous callbacks with one parameter.
 *
 * @param <T> The type of the parameter for the callback.
 */
public interface FirebaseCallback<T> extends NetworkCallback {

    void onCallback(T data);

}