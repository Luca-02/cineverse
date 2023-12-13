package com.example.cineverse.data.source.content;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.content.AbstractContentApiResponse;
import com.example.cineverse.data.source.api.BaseResponseCallback;

public interface SectionContentResponseCallback<T extends AbstractContent>
        extends BaseResponseCallback<AbstractContentApiResponse<T>> {
}
