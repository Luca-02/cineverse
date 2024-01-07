package com.example.cineverse.viewmodel.review;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.cineverse.data.model.content.AbstractContent;
import com.example.cineverse.data.model.review.Review;
import com.example.cineverse.data.model.review.UserReview;
import com.example.cineverse.data.source.review.ReviewFirebaseCallback;
import com.example.cineverse.repository.review.ReviewRepository;
import com.example.cineverse.viewmodel.verified_account.VerifiedAccountViewModel;

import java.util.List;

public class ReviewViewModel
        extends VerifiedAccountViewModel
        implements ReviewFirebaseCallback {

    private final ReviewRepository reviewRepository;
    private MutableLiveData<Review> currentUserReviewLiveData;
    private MutableLiveData<Boolean> addedReviewLiveData;
    private MutableLiveData<Boolean> removedReviewLiveData;
    private MutableLiveData<List<UserReview>> recentContentReviewLiveData;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(
                application.getApplicationContext(), userRepository.getCurrentUser(), this);
    }

    public MutableLiveData<Review> getCurrentUserReviewLiveData() {
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

    public MutableLiveData<List<UserReview>> getRecentContentReviewLiveData() {
        if (recentContentReviewLiveData == null) {
            recentContentReviewLiveData = new MutableLiveData<>();
        }
        return recentContentReviewLiveData;
    }

    public void getContentReviewOfCurrentUser(AbstractContent content) {
        reviewRepository.getContentReviewOfCurrentUser(content);
    }

    public void addContentReviewOfCurrentUser(AbstractContent content, Review oldReview, Review newReview) {
        if (!newReview.equals(oldReview)) {
            reviewRepository.addContentReviewOfCurrentUser(content, newReview);
            getRecentContentReview(content);
        }
    }

    public void getRecentContentReview(AbstractContent content) {
        reviewRepository.getRecentContentReview(content);
    }

    public void removeContentReviewOfCurrentUser(AbstractContent content) {
        reviewRepository.removeContentReviewOfCurrentUser(content);
        getRecentContentReview(content);
    }

    @Override
    public void onContentReviewOfUser(Review review) {
        getCurrentUserReviewLiveData().postValue(review);
    }

    @Override
    public void onAddedContentReviewOfUser(boolean added) {
        getAddedReviewLiveData().postValue(added);
    }

    @Override
    public void onRemovedContentReviewOfUser(boolean removed) {
        getRemovedReviewLiveData().postValue(removed);
        if (removed) {
            getCurrentUserReviewLiveData().postValue(null);
        }
    }

    @Override
    public void onRecentContentReview(List<UserReview> userReviewList) {
        if (userReviewList != null) {
            getRecentContentReviewLiveData().postValue(userReviewList);
        }
    }

    @Override
    public void onContentReviewOfContent(List<UserReview> userReviewList, int pageSize, long lastTimestamp) {

    }

}
