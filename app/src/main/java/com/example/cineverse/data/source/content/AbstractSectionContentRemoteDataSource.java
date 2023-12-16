package com.example.cineverse.data.source.content;

import android.content.Context;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.source.api.TMDBRemoteDataSource;

import java.util.Locale;

import retrofit2.Call;

/**
 * The {@link AbstractSectionContentRemoteDataSource} class extends the
 * {@link TMDBRemoteDataSource} and implements the
 * {@link ISectionContentRemoteDataSource} interface. It provides common
 * functionality for remote data sources related to section content.
 *
 * @param <T> The type of content, extending {@link AbstractContent}.
 */
public abstract class AbstractSectionContentRemoteDataSource<T extends AbstractContent>
        extends TMDBRemoteDataSource
        implements ISectionContentRemoteDataSource {

    private SectionContentRemoteResponseCallback<T> callback;

    public AbstractSectionContentRemoteDataSource(Context context) {
        super(context);
    }

    /**
     * Sets the callback for handling remote response.
     *
     * @param callback The callback.
     */
    public void setCallback(SectionContentRemoteResponseCallback<T> callback) {
        this.callback = callback;
    }

    /**
     * Gets the region based on the default locale.
     *
     * @return The region.
     */
    public String getRegion() {
        return Locale.getDefault().getCountry();
    }

    /**
     * Handles the API call for fetching poster data and invokes the callback.
     *
     * @param call The API call.
     */
    protected <A extends AbstractContentResponse<T>> void handlePosterApiCall(Call<A> call) {
        if (callback != null) {
            handlePosterApiCall(call, callback);
        }
    }

    /**
     * Gets the section associated with the remote data source.
     *
     * @return The section.
     */
    public String getSection() {
        return SectionTypeMappingManager.getSection(getClass());
    }

}
