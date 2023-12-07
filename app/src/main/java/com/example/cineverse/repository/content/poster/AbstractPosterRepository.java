package com.example.cineverse.repository.content.poster;

import android.content.Context;

import com.example.cineverse.R;
import com.example.cineverse.data.model.content.poster.AbstractPoster;
import com.example.cineverse.data.source.content.poster.PosterContentResponseCallback;

import java.util.Locale;

public abstract class AbstractPosterRepository<T extends AbstractPoster> {

    protected final String bearerAccessTokenAuth;
    protected final String region;
    protected final PosterContentResponseCallback<T> callback;

    public AbstractPosterRepository(Context context,
                                    PosterContentResponseCallback<T> callback) {
        bearerAccessTokenAuth =
                "Bearer " + context.getString(R.string.access_token_auth);
        Locale locale = Locale.getDefault();
        region = locale.getCountry();
        this.callback = callback;
    }

}
