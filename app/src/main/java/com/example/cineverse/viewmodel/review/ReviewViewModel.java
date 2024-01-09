package com.example.cineverse.viewmodel.review;

import static com.example.cineverse.utils.constant.GlobalConstant.RECENT_LIMIT_COUNT;
import static com.example.cineverse.utils.constant.GlobalConstant.REVIEW_PAGE_COUNT;
import static com.example.cineverse.utils.constant.GlobalConstant.START_TIMESTAMP_VALUE;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.review.ReviewFirebaseCallback;
import com.example.cineverse.repository.review.ReviewRepository;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReviewViewModel
        extends VerifiedAccountViewModel
        implements ReviewFirebaseCallback {

    private final ReviewRepository reviewRepository;
    private MutableLiveData<Double> contentRatingLiveData;
    private MutableLiveData<UserReview> currentUserReviewLiveData;
    private MutableLiveData<Boolean> addedReviewLiveData;
    private MutableLiveData<Boolean> removedReviewLiveData;
    private MutableLiveData<List<UserReview>> pagedContentReviewLiveData;
    private long lastTimestamp;
    private boolean isLoading;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(
                application.getApplicationContext(), userRepository.getCurrentUser(), this);
        lastTimestamp = START_TIMESTAMP_VALUE;
        isLoading = false;
    }

    public MutableLiveData<Double> getContentRatingLiveData() {
        if (contentRatingLiveData == null) {
            contentRatingLiveData = new MutableLiveData<>();
        }
        return contentRatingLiveData;
    }

    public MutableLiveData<UserReview> getCurrentUserReviewLiveData() {
        if (currentUserReviewLiveData == null) {
            currentUserReviewLiveData = new MutableLiveData<>();
        }
        return currentUserReviewLiveData;
    }

    public MutableLiveData<Boolean> getAddedReviewLiveData() {
        if (addedReviewLiveData == null) {
            addedReviewLiveData = new MutableLiveData<>();
        }
        return addedReviewLiveData;
    }

    public MutableLiveData<Boolean> getRemovedReviewLiveData() {
        if (removedReviewLiveData == null) {
            removedReviewLiveData = new MutableLiveData<>();
        }
        return removedReviewLiveData;
    }

    public MutableLiveData<List<UserReview>> getPagedContentReviewLiveData() {
        if (pagedContentReviewLiveData == null) {
            pagedContentReviewLiveData = new MutableLiveData<>();
        }
        return pagedContentReviewLiveData;
    }

    public long getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(long lastTimestamp) {
        this.lastTimestamp = lastTimestamp;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void getContentRating(AbstractContent content) {
        reviewRepository.getContentRating(content);
    }

    public void getContentReviewOfCurrentUser(AbstractContent content) {
        reviewRepository.getContentReviewOfCurrentUser(content);
    }

    public void addContentReviewOfCurrentUser(AbstractContent content, Review oldReview, Review newReview) {
        if (!newReview.equals(oldReview)) {
            reviewRepository.addContentReviewOfCurrentUser(content, newReview);
            updateContentReviewOfContent(content);
        }
    }

    public void removeContentReviewOfCurrentUser(AbstractContent content, @NonNull Review review) {
        reviewRepository.removeContentReviewOfCurrentUser(content, review);
        updateContentReviewOfContent(content);
    }

    public void getPagedContentReviewOfContent(AbstractContent content) {
        long timestamp = getLastTimestamp();
        int pageSize = (timestamp == START_TIMESTAMP_VALUE) ? RECENT_LIMIT_COUNT : REVIEW_PAGE_COUNT;
        reviewRepository.getPagedContentReviewOfContent(content, pageSize, getLastTimestamp());
    }

    private void updateContentReviewOfContent(AbstractContent content) {
        setLastTimestamp(START_TIMESTAMP_VALUE);
        getPagedContentReviewLiveData().setValue(new ArrayList<>());
        getPagedContentReviewOfContent(content);
    }

    @Override
    public void onContentRating(Double rating) {
        contentRatingLiveData.postValue(rating);
    }

    @Override
    public void onContentReviewOfUser(UserReview userReview) {
        getCurrentUserReviewLiveData().postValue(userReview);
    }

    @Override
    public void onAddedContentReviewOfUser(UserReview userReview) {
        getAddedReviewLiveData().postValue(userReview != null);
        getCurrentUserReviewLiveData().postValue(userReview);
    }

    @Override
    public void onRemovedContentReviewOfUser(boolean removed) {
        getRemovedReviewLiveData().postValue(removed);
        if (removed) {
            getCurrentUserReviewLiveData().postValue(null);
        }
    }

    @Override
    public void onPagedContentReviewOfContent(List<UserReview> userReviewList, long lastTimestamp) {
        if (userReviewList != null) {
            setLastTimestamp(lastTimestamp);
            List<UserReview> list = getPagedContentReviewLiveData().getValue();
            if (list == null) {
                list = new ArrayList<>(userReviewList);
                getPagedContentReviewLiveData().postValue(list);
            } else {
                list.addAll(userReviewList);
                getPagedContentReviewLiveData().postValue(list);
            }
        }
    }

}
