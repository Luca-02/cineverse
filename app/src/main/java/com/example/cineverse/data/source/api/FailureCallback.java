package com.example.cineverse.data.source.api;

import com.example.cineverse.data.model.api.Failure;

/**
 * The {@link FailureCallback} interface provides a callback mechanism for handling failures
 * in API requests.
 */
public interface FailureCallback {
    /**
     * Callback method invoked when a failure occurs in an API request.
     *
     * @param failure The {@link Failure} object containing information about the failure.
     */
    void onFailure(Failure failure);
}
