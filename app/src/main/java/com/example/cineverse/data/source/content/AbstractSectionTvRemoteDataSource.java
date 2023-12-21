package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.model.content.section.Tv;
import com.example.cineverse.data.model.content.section.TvResponse;
import com.example.cineverse.service.api.TvApiService;
import com.example.cineverse.utils.ServiceLocator;

import retrofit2.Call;

/**
 * The {@link AbstractSectionTvRemoteDataSource} class extends the
 * {@link AbstractSectionContentRemoteDataSource} and provides common
 * functionality for remote data sources related to TV section content.
 */
public abstract class AbstractSectionTvRemoteDataSource
        extends AbstractSectionContentRemoteDataSource<Tv> {

    protected final TvApiService movieApiService;

    public AbstractSectionTvRemoteDataSource(Context context) {
        super(context);
        movieApiService = ServiceLocator.getInstance().getTvApiService();
    }


    /**
     * {@inheritDoc}
     * <p>
     * This method initiates the process of fetching tv series data from the remote data source.
     * It creates and executes the Retrofit API call to get the tv series data.
     * </p>
     *
     * @param page The page number of the data to fetch (if applicable).
     * @see #createApiCall(int) The method responsible for creating the Retrofit API call.
     */
    @Override
    public void fetch(int page) {
        Call<TvResponse> call = createApiCall(page);
        handlePosterApiCall(call);
    }

    /**
     * Creates a Retrofit API call for fetching tv series data from the remote data source.
     *
     * @return A Retrofit {@link Call} representing the API call for fetching {@link TvResponse}.
     */
    protected abstract Call<TvResponse> createApiCall(int page);

}
