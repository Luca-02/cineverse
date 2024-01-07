package com.example.cineverse.data.source.details.section;

import static com.example.cineverse.utils.constant.Api.AGGREGATE_CREDITS;
import static com.example.cineverse.utils.constant.Api.SIMILAR;
import static com.example.cineverse.utils.constant.Api.VIDEOS;

import android.content.Context;

import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.data.source.details.AbstractContentDetailsRemoteDataSource;

import retrofit2.Call;

public class TvDetailsRemoteDataSource
        extends AbstractContentDetailsRemoteDataSource<TvDetails> {

    public TvDetailsRemoteDataSource(Context context) {
        super(context);
    }

    public Call<TvDetails> createApiCall(int tvId) {
        String appendToResponse = AGGREGATE_CREDITS + "," + VIDEOS + "," + SIMILAR;
        return detailsApiService.getTvDetails(
                tvId, appendToResponse, getLanguage(), getBearerAccessTokenAuth());
    }

}
