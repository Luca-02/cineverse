package com.example.cineverse.data.source.api;

import com.example.cineverse.data.model.Failure;

public interface FailureCallback {
    void onFailure(Failure failure);
}
