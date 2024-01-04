package com.example.cineverse.service.firebase;

/**
 * The {@link FirebaseCallback <T>} interface extends {@link NetworkCallback} and defines a method
 * to handle asynchronous callbacks with one parameter.
 *
 * @param <T> The type of the parameter for the callback.
 */
public interface FirebaseCallback<T> extends NetworkCallback {

    void onCallback(T data);

}