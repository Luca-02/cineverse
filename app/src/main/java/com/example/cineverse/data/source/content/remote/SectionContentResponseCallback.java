package com.example.cineverse.data.source.content.remote;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentResponse;
import com.example.cineverse.data.source.api.BaseResponseCallback;

public interface SectionContentResponseCallback<T extends AbstractContent>
        extends BaseResponseCallback<AbstractContentResponse<T>> {
}
