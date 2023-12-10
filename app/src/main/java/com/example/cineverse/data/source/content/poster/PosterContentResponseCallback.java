package com.example.cineverse.data.source.content.poster;

import com.example.cineverse.data.model.content.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentApiResponse;

public interface PosterContentResponseCallback<T extends AbstractContent> {
    void onResponse(AbstractContentApiResponse<T> response);
    void onFailure(Failure failure);
}
