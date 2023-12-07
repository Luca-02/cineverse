package com.example.cineverse.data.source.content.poster;

import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.data.model.content.poster.AbstractPoster;
import com.example.cineverse.data.model.content.poster.AbstractPosterApiResponse;

public interface PosterContentResponseCallback<T extends AbstractPoster> {
    void onResponse(AbstractPosterApiResponse<T> response);
    void onFailure(Failure failure);
}
