package com.example.cineverse.data.source.details.section;

import static com.example.cineverse.utils.constant.Api.APPEND_TO_RESPONSE_DEFAULT_PARAMETER;

import android.content.Context;

import com.example.cineverse.data.model.details.section.TvDetails;
import com.example.cineverse.data.source.details.AbstractContentDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;

import retrofit2.Call;

public class TvDetailsRemoteDataSource
        extends AbstractContentDetailsRemoteDataSource<TvDetails> {

    public TvDetailsRemoteDataSource(Context context) {
        super(context);
    }

    public Call<TvDetails> createApiCall(int tvId) {
        return detailsApiService.getTvDetails(
                tvId, APPEND_TO_RESPONSE_DEFAULT_PARAMETER, getLanguage(), getBearerAccessTokenAuth());
    }

}
