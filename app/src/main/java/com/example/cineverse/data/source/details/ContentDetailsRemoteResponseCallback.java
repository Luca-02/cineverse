package com.example.cineverse.data.source.details;

import com.example.cineverse.data.model.details.section.ContentDetailsApiResponse;
import com.example.cineverse.data.source.api.BaseRemoteResponseCallback;

public interface ContentDetailsRemoteResponseCallback<T extends ContentDetailsApiResponse>
        extends BaseRemoteResponseCallback<T> {
}
