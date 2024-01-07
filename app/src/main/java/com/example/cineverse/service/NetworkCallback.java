package com.example.cineverse.service;

/**
 * The {@link NetworkCallback} interface defines a method to handle cases when the network is
 * unavailable.
 */
public interface NetworkCallback {

    /**
     * Invoked when there is a network error
     */
    void onNetworkUnavailable();

}
