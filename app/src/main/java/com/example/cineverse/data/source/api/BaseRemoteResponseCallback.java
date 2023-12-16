package com.example.cineverse.data.source.api;

import com.example.cineverse.data.model.api.ApiResponse;

/**
 * The {@link BaseRemoteResponseCallback} interface extends the {@link FailureCallback} interface
 * and provides a callback mechanism for handling both successful and failed remote responses
 * in API requests.
 *
 * @param <T> The type of the expected API response, extending {@link ApiResponse}.
 */
public interface BaseRemoteResponseCallback<T extends ApiResponse>
        extends FailureCallback {
    /**
     * Callback method invoked when a remote response is received successfully.
     *
     * @param response The response object of type {@link T} extending {@link ApiResponse}.
     */
    void onRemoteResponse(T response);
}
