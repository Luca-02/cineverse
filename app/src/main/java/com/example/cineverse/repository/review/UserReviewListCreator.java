package com.example.cineverse.repository.review;

import com.example.cineverse.data.model.api.Failure;
import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.details.section.IContentDetails;
import com.example.cineverse.data.model.review.ContentUserReview;
import com.example.cineverse.data.source.details.AbstractContentDetailsRemoteDataSource;
import com.example.cineverse.data.source.details.ContentDetailsRemoteResponseCallback;
import com.example.cineverse.data.source.review.ReviewFirebaseCallback;

import java.util.ArrayList;
import java.util.List;

public class UserReviewListCreator<T extends IContentDetails>
        implements ContentDetailsRemoteResponseCallback<T> {


    private final List<ContentUserReview> startList;
    private final String contentType;
    private final AbstractContentDetailsRemoteDataSource<T> remoteDataSource;
    private final ReviewFirebaseCallback firebaseCallback;
    private final List<ContentUserReview> resultList;
    private int index;

    public UserReviewListCreator(
            List<ContentUserReview> startList,
            String contentType,
            AbstractContentDetailsRemoteDataSource<T> remoteDataSource,
            ReviewFirebaseCallback firebaseCallback) {
        this.startList = startList;
        this.contentType = contentType;
        this.remoteDataSource = remoteDataSource;
        this.firebaseCallback = firebaseCallback;
        remoteDataSource.setRemoteResponseCallback(this);
        resultList = new ArrayList<>();
        index = 0;
    }

    public void create() {
        if (startList != null && startList.size() > 0) {
            remoteDataSource.fetchDetails(startList.get(index).getContent().getId());
        } else {
            firebaseCallback.onUserReviewList(startList, contentType);
        }
    }

    @Override
    public void onRemoteResponse(T response) {
        startList.get(index).setContent((AbstractContent) response);
        resultList.add(startList.get(index));
        index++;
        if (index < startList.size()) {
            remoteDataSource.fetchDetails(startList.get(index).getContent().getId());
        } else {
            firebaseCallback.onUserReviewList(resultList, contentType);
        }
    }

    @Override
    public void onFailure(Failure failure) {
        firebaseCallback.onUserReviewList(null, contentType);
    }

}
