package com.example.cineverse.data.source.content.remote;

import android.content.Context;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;

import java.util.Locale;

import retrofit2.Call;

public abstract class AbstractSectionContentRemoteDataSource<T extends AbstractContent>
        extends TMDBRemoteDataSource
        implements ISectionContentRemoteDataSource {

    protected String region;
    private SectionContentResponseCallback<T> callback;

    public AbstractSectionContentRemoteDataSource(Context context) {
        super(context);
        region = Locale.getDefault().getCountry();
    }

    public void setCallback(SectionContentResponseCallback<T> callback) {
        this.callback = callback;
    }

    protected <A extends AbstractContentResponse<T>> void handlePosterApiCal(Call<A> call) {
        if (callback != null) {
            handlePosterApiCal(call, callback);
        }
    }

    public abstract String getSection();

}
