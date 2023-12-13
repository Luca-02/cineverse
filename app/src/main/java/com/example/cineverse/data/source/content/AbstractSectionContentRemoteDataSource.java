package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentApiResponse;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;

import java.util.Locale;

import retrofit2.Call;

public abstract class AbstractSectionContentRemoteDataSource<T extends AbstractContent>
        extends TMDBRemoteDataSource {

    protected String region;
    private final SectionContentResponseCallback<T> callback;

    public AbstractSectionContentRemoteDataSource(Context context,
                                                  SectionContentResponseCallback<T> callback) {
        super(context);
        region = Locale.getDefault().getCountry();
        this.callback = callback;
    }

    protected <A extends AbstractContentApiResponse<T>> void handlePosterApiCal(Call<A> call) {
        handlePosterApiCal(call, callback);
    }

}
