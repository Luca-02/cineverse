package com.example.cineverse.data.source.api;

import com.example.cineverse.data.model.ApiResponse;

public interface BaseResponseCallback<T extends ApiResponse>
        extends FailureCallback {
    void onResponse(T response);
}
